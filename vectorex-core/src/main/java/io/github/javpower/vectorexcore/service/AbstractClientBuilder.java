package io.github.javpower.vectorexcore.service;

import io.github.javpower.vectorexcore.VectoRexClient;
import io.github.javpower.vectorexcore.annotation.VectoRexCollection;
import io.github.javpower.vectorexcore.converter.VecroRexConverter;
import io.github.javpower.vectorexcore.entity.VectoRexEntity;
import io.github.javpower.vectorexcore.model.VectoRexProperties;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public abstract class AbstractClientBuilder  {

    @Setter
    protected VectoRexProperties properties;
    protected VectoRexClient client;
    private final static String CLASS = "*.class";

    public void initialize() {
        if (properties.isEnable()) {
            client = new VectoRexClient(properties.getUri());
            // 初始化逻辑
            handler();
        }
    }

    public void handler() {
        if (Objects.isNull(client)) {
            log.warn("initialize handler over!");
        }
        List<Class<?>> classes = getClass(properties.getPackages());
        if (classes.isEmpty()) {
            log.warn("no any collections have been initialized, see if the [packages] parameter is configured correctly. :( !");
            return;
        }
        performBusinessLogic(classes);
    }

    public VectoRexClient getClient() {
        return client;
    }

    // 获取指定包下实体类
    // 获取指定包下实体类（去框架化实现）
    private List<Class<?>> getClass(List<String> packages) {
        return Optional.ofNullable(packages)
                .orElseThrow(() -> new RuntimeException("model package is null, please configure the [packages] parameter"))
                .stream()
                .flatMap(pg -> scanPackageClasses(pg).stream())
                .filter(clazz -> clazz.getAnnotation(VectoRexCollection.class) != null)
                .collect(Collectors.toList());
    }

    // 通用包扫描方法
    private List<Class<?>> scanPackageClasses(String basePackage) {
        String path = basePackage.replace('.', '/');
        Enumeration<java.net.URL> resources;
        try {
            resources = Thread.currentThread()
                    .getContextClassLoader()
                    .getResources(path);
        } catch (IOException e) {
            throw new RuntimeException("Scan package failed: " + basePackage, e);
        }

        List<Class<?>> classes = new ArrayList<>();
        while (resources.hasMoreElements()) {
            java.net.URL resource = resources.nextElement();
            classes.addAll(scanDirectory(
                    new File(resource.getFile().replaceAll("%20", " ")),
                    basePackage
            ));
        }
        return classes;
    }

    // 递归扫描目录
    private List<Class<?>> scanDirectory(File directory, String packageName) {
        List<Class<?>> classes = new ArrayList<>();
        if (!directory.exists()) return classes;

        File[] files = directory.listFiles();
        if (files == null) return classes;

        for (File file : files) {
            if (file.isDirectory()) {
                classes.addAll(scanDirectory(file,
                        packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                String className = packageName + '.'
                        + file.getName().substring(0, file.getName().length() - 6);
                try {
                    classes.add(Class.forName(className));
                } catch (ClassNotFoundException e) {
                    log.warn("Class not found: {}", className);
                }
            }
        }
        return classes;
    }


    // 缓存 + 是否构建集合
    public void performBusinessLogic(List<Class<?>> annotatedClasses) {
        for (Class<?> milvusClass : annotatedClasses) {
            VectoRexEntity milvusEntity = VecroRexConverter.convert(milvusClass);
            client.createCollection(milvusEntity);
        }
    }
}