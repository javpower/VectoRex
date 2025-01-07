package io.github.javpower.vectorexbootstater.builder;

import io.github.javpower.vectorex.keynote.core.DbData;
import io.github.javpower.vectorex.keynote.core.VectorSearchResult;
import io.github.javpower.vectorex.keynote.storage.MapDBStorage;
import io.github.javpower.vectorexbootstater.core.FieldFunction;
import io.github.javpower.vectorexbootstater.core.VectoRexResult;
import io.github.javpower.vectorexcore.VectoRexClient;
import io.github.javpower.vectorexcore.cache.VecroRexCache;
import io.github.javpower.vectorexcore.util.GsonUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 搜索构建器内部类，用于构建搜索请求
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Slf4j
public class LambdaQueryWrapper<T> extends VectoRexConditionBuilder<T>  {

    private Class<T> entityType;
    private String collectionName;
    private String annsField;
    private int topK=1;
    private List<Float> vector;
    private VectoRexClient client;

    public LambdaQueryWrapper(VectoRexClient client,String collectionName,Class<T> entityType) {
        this.client=client;
        this.collectionName=collectionName;
        this.entityType=entityType;
    }
    public LambdaQueryWrapper<T> topK(Integer topK) {
        this.setTopK(topK);
        return this;
    }
    public LambdaQueryWrapper<T> vector(String annsField, List<Float> vector) {
        this.annsField=annsField;
        this.vector=vector;
        return this;
    }
    public LambdaQueryWrapper<T> vector(FieldFunction<T,?> annsField, List<Float> vector) {
        this.annsField=annsField.getFieldName(annsField);
        this.vector=vector;
        return this;
    }
    // 重写条件构建方法，使用 super 调用父类的实现
    public LambdaQueryWrapper<T> eq(String fieldName, Object value) {
        super.eq(fieldName, value);
        return this;
    }

    public LambdaQueryWrapper<T> eq(FieldFunction<T, ?> fieldName, Object value) {
        super.eq(fieldName, value);
        return this;
    }

    public LambdaQueryWrapper<T> ne(String fieldName, Object value) {
        super.ne(fieldName, value);
        return this;
    }

    public LambdaQueryWrapper<T> ne(FieldFunction<T, ?> fieldName, Object value) {
        super.ne(fieldName, value);
        return this;
    }

    public LambdaQueryWrapper<T> gt(String fieldName, Comparable value) {
        super.gt(fieldName, value);
        return this;
    }

    public LambdaQueryWrapper<T> gt(FieldFunction<T, Comparable> fieldName, Comparable value) {
        super.gt(fieldName, value);
        return this;
    }

    public LambdaQueryWrapper<T> ge(String fieldName, Comparable value) {
        super.ge(fieldName, value);
        return this;
    }

    public LambdaQueryWrapper<T> ge(FieldFunction<T, Comparable> fieldName, Comparable value) {
        super.ge(fieldName, value);
        return this;
    }

    public LambdaQueryWrapper<T> lt(String fieldName, Comparable value) {
        super.lt(fieldName, value);
        return this;
    }

    public LambdaQueryWrapper<T> lt(FieldFunction<T, Comparable> fieldName, Comparable value) {
        super.lt(fieldName, value);
        return this;
    }

    public LambdaQueryWrapper<T> le(String fieldName, Comparable value) {
        super.le(fieldName, value);
        return this;
    }

    public LambdaQueryWrapper<T> le(FieldFunction<T, Comparable> fieldName, Comparable value) {
        super.le(fieldName, value);
        return this;
    }

    public LambdaQueryWrapper<T> between(String fieldName, Comparable start, Comparable end) {
        super.between(fieldName, start, end);
        return this;
    }

    public LambdaQueryWrapper<T> between(FieldFunction<T, Comparable> fieldName, Comparable start, Comparable end) {
        super.between(fieldName, start, end);
        return this;
    }

    public LambdaQueryWrapper<T> in(String fieldName, List<?> values) {
        super.in(fieldName, values);
        return this;
    }

    public LambdaQueryWrapper<T> in(FieldFunction<T, ?> fieldName, List<?> values) {
        super.in(fieldName, values);
        return this;
    }

    public LambdaQueryWrapper<T> like(String fieldName, String value) {
        super.like(fieldName, value);
        return this;
    }

    public LambdaQueryWrapper<T> like(FieldFunction<T, String> fieldName, String value) {
        super.like(fieldName, value);
        return this;
    }
    public List<VectoRexResult<T>> query() {
        MapDBStorage store = client.getStore(collectionName);
        List<VectorSearchResult> results;
        if (vector != null) {
            results = store.search(annsField, vector, topK, super.filters);
        } else {
            results = store.query(super.filters);
        }
        Map<String, String> toFiled = VecroRexCache.toField.get(entityType.getName());
        return results.stream().map(result -> {
            VectoRexResult<T> vectoRexResult = new VectoRexResult<>();
            Float score = result.getScore();
            DbData data = result.getData();
            Map<String, Object> metadata = data.getMetadata();
            Map<String, Object> properties = new HashMap<>();
            for (Map.Entry<String, Object> stringObjectEntry : metadata.entrySet()) {
                if (toFiled.containsKey(stringObjectEntry.getKey())) {
                    properties.put(toFiled.get(stringObjectEntry.getKey()), stringObjectEntry.getValue());
                }
            }
            T o = GsonUtil.convertMapToType(properties, entityType);
            vectoRexResult.setEntity(o);
            vectoRexResult.setScore(score);
            return vectoRexResult;
        }).collect(Collectors.toList());
    }


}