package io.github.javpower.vectorexbootstater.builder;

import io.github.javpower.vectorexcore.converter.VecroRexConverter;
import io.github.javpower.vectorexcore.entity.VectoRexEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

/**
 * 搜索构建器内部类，用于构建搜索请求
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Slf4j
public class LambdaQueryWrapper<T> extends VectoRexConditionBuilder<T>  {
    private VectoRexEntity conversionCache;

    private Class<T> entityType;
    private String collectionName;

    private String annsField;
    private int topK;

    public LambdaQueryWrapper() {

    }
    public LambdaQueryWrapper(Class<T> entityType) {
        this.entityType = entityType;
        this.conversionCache = VecroRexConverter.convert(entityType);
        this.collectionName = conversionCache.getCollectionName();
    }
    public LambdaQueryWrapper(Class<T> entityType, String collectionName) {
        this.entityType = entityType;
        this.conversionCache = VecroRexConverter.convert(entityType);
        this.collectionName = collectionName;
    }

}