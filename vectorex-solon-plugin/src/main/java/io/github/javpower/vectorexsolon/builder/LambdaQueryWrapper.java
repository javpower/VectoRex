package io.github.javpower.vectorexsolon.builder;

import io.github.javpower.vectorex.keynote.core.DbData;
import io.github.javpower.vectorex.keynote.core.VectorSearchResult;
import io.github.javpower.vectorex.keynote.storage.MapDBStorage;
import io.github.javpower.vectorexcore.VectoRexClient;
import io.github.javpower.vectorexcore.cache.VecroRexCache;
import io.github.javpower.vectorexcore.util.GsonUtil;
import io.github.javpower.vectorexsolon.core.FieldFunction;
import io.github.javpower.vectorexsolon.core.VectoRexResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author cxc
 */
public class LambdaQueryWrapper<T> extends VectoRexConditionBuilder<T> {

    private Class<T> entityType;
    private String collectionName;
    private String annsField;
    private int topK = 1;
    private List<Float> vector;
    private VectoRexClient client;
    private boolean enableScore = true;  // 新增属性：是否启用评分

    // 构造函数
    public LambdaQueryWrapper(VectoRexClient client, String collectionName, Class<T> entityType) {
        this.client = client;
        this.collectionName = collectionName;
        this.entityType = entityType;
    }

    // TopK设置
    public LambdaQueryWrapper<T> topK(Integer topK) {
        this.topK = topK;
        return this;
    }

    // 向量设置
    public LambdaQueryWrapper<T> vector(String annsField, List<Float> vector) {
        this.annsField = annsField;
        this.vector = vector;
        return this;
    }

    public LambdaQueryWrapper<T> vector(FieldFunction<T, ?> annsField, List<Float> vector) {
        this.annsField = annsField.getFieldName(annsField);
        this.vector = vector;
        return this;
    }

    // 是否启用评分
    public LambdaQueryWrapper<T> enableScore(boolean enable) {
        this.enableScore = enable;
        return this;
    }

    // 重写所有条件方法，保持链式调用
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

    // 查询方法
    public List<VectoRexResult<T>> query() {
        MapDBStorage store = client.getStore(collectionName);
        List<VectorSearchResult> results;

        if (vector != null) {
            if (!super.filters.getOperations().isEmpty()) {
                results = store.search(annsField, vector, topK, super.filters);
            } else {
                results = store.search(annsField, vector, topK, null);
            }
        } else {
            results = store.query(super.filters);
        }

        Map<String, String> toFiled = VecroRexCache.toField.get(entityType.getName());
        return results.stream().map(result -> {
            VectoRexResult<T> vectoRexResult = new VectoRexResult<>();

            Float score = enableScore ? result.getScore() : null;
            DbData data = result.getData();
            Map<String, Object> metadata = data.getMetadata();

            Map<String, Object> properties = new HashMap<>();
            for (Map.Entry<String, Object> entry : metadata.entrySet()) {
                if (toFiled.containsKey(entry.getKey())) {
                    properties.put(toFiled.get(entry.getKey()), entry.getValue());
                }
            }

            T o = GsonUtil.convertMapToType(properties, entityType);
            vectoRexResult.setEntity(o);
            vectoRexResult.setScore(score);

            return vectoRexResult;
        }).collect(Collectors.toList());
    }

    // 重写getFieldName方法
    protected String getFieldName(FieldFunction<T, ?> fieldName) {
        return fieldName.getFieldName(fieldName);
    }
}
