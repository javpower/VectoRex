package io.github.javpower.vectorexcore.service;

import io.github.javpower.vectorexcore.VectoRexClient;
import io.github.javpower.vectorexcore.annotation.VectoRexCollection;
import io.github.javpower.vectorexcore.converter.VecroRexConverter;
import io.github.javpower.vectorexcore.entity.VectoRexEntity;
import io.github.javpower.vectorexcore.model.VectoRexProperties;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;

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
    private List<Class<?>> getClass(List<String> packages) {
        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        return Optional.ofNullable(packages)
                .orElseThrow(() -> new RuntimeException("model package is null, please configure the [packages] parameter"))
                .stream()
                .map(pg -> {
                    List<Class<?>> res = new ArrayList<>();
                    String pattern = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
                            + ClassUtils.convertClassNameToResourcePath(pg + ".") + CLASS;
                    try {
                        Resource[] resources = resourcePatternResolver.getResources(pattern);
                        MetadataReaderFactory readerFactory = new CachingMetadataReaderFactory(resourcePatternResolver);
                        for (Resource resource : resources) {
                            MetadataReader reader = readerFactory.getMetadataReader(resource);
                            String classname = reader.getClassMetadata().getClassName();
                            Class<?> clazz = Class.forName(classname);
                            VectoRexCollection annotation = clazz.getAnnotation(VectoRexCollection.class);
                            if (annotation != null) {
                                res.add(clazz);
                            }
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    return res;
                }).flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    // 缓存 + 是否构建集合
    public void performBusinessLogic(List<Class<?>> annotatedClasses) {
        for (Class<?> milvusClass : annotatedClasses) {
            VectoRexEntity milvusEntity = VecroRexConverter.convert(milvusClass);
            client.createCollection(milvusEntity);
        }
    }
}