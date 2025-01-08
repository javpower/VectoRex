package io.github.javpower.vectorexserver.controller;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/metrics")
public class MetricsController {

    private final MeterRegistry registry;

    public MetricsController(MeterRegistry registry) {
        this.registry = registry;
    }

    @GetMapping("/jvm")
    public Map<String, Object> getJvmMetrics() {
        Map<String, Object> metrics = new HashMap<>();

        // 获取 JVM 内存指标
        metrics.put("jvm.memory.used", registry.get("jvm.memory.used").gauge().value());
        metrics.put("jvm.memory.max", registry.get("jvm.memory.max").gauge().value());

        // 获取线程指标
        metrics.put("jvm.threads.live", registry.get("jvm.threads.live").gauge().value());
        metrics.put("jvm.threads.peak", registry.get("jvm.threads.peak").gauge().value());

        // 获取 GC 指标
        metrics.put("jvm.gc.memory.allocated", registry.get("jvm.gc.memory.allocated").counter().count());
        metrics.put("jvm.gc.pause", registry.get("jvm.gc.pause").timer().totalTime(TimeUnit.MINUTES));

        return metrics;
    }

}