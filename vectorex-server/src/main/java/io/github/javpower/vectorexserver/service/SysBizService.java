package io.github.javpower.vectorexserver.service;

import io.github.javpower.vectorexserver.util.AesUtil;
import io.github.javpower.vectorexserver.util.HttpRequestUtil;
import org.mapdb.DB;
import org.mapdb.HTreeMap;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class SysBizService {
    private static final long TOKEN_EXPIRATION_TIME = 3600_000*2; // 2 小时
    private HTreeMap<String, ConcurrentHashMap<String, AtomicInteger>> VectoRexDashboard;
    private HTreeMap<String, String> VectoRexAccount;
    private HTreeMap<String, Long> token_time;
    private DB db;
    public SysBizService(DB db) {
        this.db=db;
        VectoRexDashboard=(HTreeMap<String, ConcurrentHashMap<String,AtomicInteger>>)db.hashMap("VectoRexDashboard").createOrOpen();
        VectoRexAccount=(HTreeMap<String, String>)db.hashMap("VectoRexAccount").createOrOpen();
        token_time=(HTreeMap<String, Long>)db.hashMap("VectoRexTokenTime").createOrOpen();
    }

    // 增加当日查询数量
    public void incrementTodayQueryCount() {
        String today = LocalDate.now().toString();
        // 同时更新一周内查询数量
        incrementWeeklyQueryCount(today);
        db.commit();
    }

    //添加用户
    public void addUser(String username, String password) {
        VectoRexAccount.put(username, password);
        db.commit();
    }
    //判断是否存在
    public boolean existUser(String username) {
        return VectoRexAccount.containsKey(username);
    }
    // 验证用户
    public boolean verifyUser(String username, String password) {
        String storedPassword = VectoRexAccount.get(username);
        return storedPassword != null && storedPassword.equals(password);
    }

    // 获取一周内每日查询数量
    public Map<String, Object> getWeeklyQueryCounts() {
        ConcurrentHashMap<String, AtomicInteger> weeklyQueryMap = VectoRexDashboard.get("weeklyQueries");
        Map<String, Object> result = new HashMap<>();
        // 获取一周内每日查询数量
        if (weeklyQueryMap != null) {
            for (Map.Entry<String, AtomicInteger> entry : weeklyQueryMap.entrySet()) {
                result.put(entry.getKey(), entry.getValue().get());
            }
        }
        return result;
    }

    // 增加一周内查询数量
    private void incrementWeeklyQueryCount(String date) {
        ConcurrentHashMap<String, AtomicInteger> weeklyQueryMap = VectoRexDashboard.computeIfAbsent("weeklyQueries", k -> new ConcurrentHashMap<>());
        AtomicInteger weeklyCount = weeklyQueryMap.computeIfAbsent(date, k -> new AtomicInteger(0));
        weeklyCount.incrementAndGet();
        // 清理超过一周的数据
        cleanOldWeeklyQueries(weeklyQueryMap);
        VectoRexDashboard.put("weeklyQueries", weeklyQueryMap);
        db.commit();
    }

    // 清理超过一周的查询记录
    private void cleanOldWeeklyQueries(ConcurrentHashMap<String, AtomicInteger> weeklyQueryMap) {
        LocalDate oneWeekAgo = LocalDate.now().minus(7, ChronoUnit.DAYS);
        weeklyQueryMap.keySet().removeIf(dateStr -> LocalDate.parse(dateStr).isBefore(oneWeekAgo));
    }

    /**
     * 生成 Token
     *
     * @param username 用户名
     * @param password 密码
     * @return 生成的 Token
     */
    public String generateToken(String username, String password) {
        // 拼接用户名、密码和时间戳
        String data = username + "|" + password+"|"+System.currentTimeMillis();
        String encrypt = AesUtil.encrypt(data);
        token_time.put(encrypt,System.currentTimeMillis());
        db.commit();
        return encrypt;
    }

    /**
     * 验证 Token 是否有效
     *
     * @param token 需要验证的 Token
     * @return 是否有效
     */
    public boolean validateToken(String token) {
        try {
            String decryptedData = AesUtil.decrypt(token);
            String[] parts = decryptedData.split("\\|");
            boolean verify = verifyUser(parts[0], parts[1]);
            if(!verify){
                return false;
            }
            // 检查 Token 是否过期
            Long tokenTime = token_time.get(token);
            if(tokenTime!=null){
                long currentTime = System.currentTimeMillis();
                if ((currentTime - tokenTime) <= TOKEN_EXPIRATION_TIME) {
                    return true;
                }
                token_time.remove(token);
                db.commit();
                return false;
            }else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }
    public void reToken(String token) {
        token_time.put(token,System.currentTimeMillis());
        db.commit();
    }
    public void delToken() throws Exception {
        String token = getToken();
        token_time.remove(token);
        db.commit();
    }

    public static String getToken() throws Exception {
        return getToken(HttpRequestUtil.getRequest());
    }

    public static String getToken(HttpServletRequest request) throws Exception {
        if (request == null) {
            throw new IllegalArgumentException("request不能为空");
        }
        // 从请求头中获取token
        String token = request.getHeader("token");
        return token;
    }


}
