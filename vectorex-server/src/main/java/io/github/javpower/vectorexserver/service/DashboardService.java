package io.github.javpower.vectorexserver.service;

import org.mapdb.DB;
import org.mapdb.HTreeMap;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class DashboardService {
    private HTreeMap<String, ConcurrentHashMap<String, AtomicInteger>> VectoRexDashboard;
    private DB db;
    public DashboardService(DB db) {
        this.db=db;
        VectoRexDashboard=(HTreeMap<String, ConcurrentHashMap<String,AtomicInteger>>)db.hashMap("VectoRexDashboard").createOrOpen();
    }

    // 增加当日查询数量
    public void incrementTodayQueryCount() {
        String today = LocalDate.now().toString();
        // 同时更新一周内查询数量
        incrementWeeklyQueryCount(today);
        db.commit();
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


}
