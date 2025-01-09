package io.github.javpower.vectorexserver.util;

import io.github.javpower.vectorex.keynote.VectorDB;
import lombok.extern.slf4j.Slf4j;
import org.mapdb.DB;
import org.mapdb.HTreeMap;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * Token工具类
 *
 * @author gc.x
 * @date 2022-1-05
 **/
@Slf4j
@Component
public class TokenUtil {
    private DB db;
    // Token 有效期（单位：毫秒）
    private static final long TOKEN_EXPIRATION_TIME = 3600_000; // 1 小时
    private HTreeMap<String, Long> TOKEN_TIME;
    public TokenUtil(){
        db=VectorDB.mapDBManager.getDb();
        TOKEN_TIME=(HTreeMap<String, Long>)db.hashMap("VectoRexTokenTime").createOrOpen();
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
        TOKEN_TIME.put(encrypt,System.currentTimeMillis());
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
            // 解密 Token
            AesUtil.decrypt(token);
            // 检查 Token 是否过期
            Long tokenTime = TOKEN_TIME.get(token);
            if(tokenTime!=null){
                long currentTime = System.currentTimeMillis();
                if ((currentTime - tokenTime) <= TOKEN_EXPIRATION_TIME) {
                    return true;
                }
                TOKEN_TIME.remove(token);
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
        TOKEN_TIME.put(token,System.currentTimeMillis());
        db.commit();
    }
    public void del(String token) {
        TOKEN_TIME.remove(token);
        db.commit();
    }

    /**
     * 从 Token 中获取密码
     *
     * @param token 需要解析的 Token
     * @return 密码
     */
    public static String getPasswordFromToken(String token) {
        try {
            String decryptedData = AesUtil.decrypt(token);
            String[] parts = decryptedData.split("\\|");
            return parts[1];
        } catch (Exception e) {
            throw new RuntimeException("Failed to get password from token", e);
        }
    }


    /**
     * 获取token
     *
     * @return
     */
    public static String getToken() throws Exception {
        return getToken(HttpRequestUtil.getRequest());
    }

    /**
     * 从请求头或者请求参数中获取token
     *
     * @param request
     * @return
     */
    public static String getToken(HttpServletRequest request) throws Exception {
        if (request == null) {
            throw new IllegalArgumentException("request不能为空");
        }
        // 从请求头中获取token
        String token = request.getHeader("token");
        return token;
    }


}
