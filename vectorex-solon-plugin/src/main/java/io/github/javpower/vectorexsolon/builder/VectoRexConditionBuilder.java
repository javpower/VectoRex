package io.github.javpower.vectorexsolon.builder;

import io.github.javpower.vectorex.keynote.query.ConditionBuilder;
import io.github.javpower.vectorexsolon.core.FieldFunction;

import java.util.List;

/**
 * Solon 版本的条件构建器
 *
 * @author cxc
 */
public abstract class VectoRexConditionBuilder<T> {

     protected ConditionBuilder filters = new ConditionBuilder();

    // 等于条件
    protected VectoRexConditionBuilder<T> eq(String fieldName, Object value) {
        filters.eq(fieldName, value);
        return this;
    }

    public VectoRexConditionBuilder<T> eq(FieldFunction<T, ?> fieldName, Object value) {
        String fn = getFieldName(fieldName);
        return eq(fn, value);
    }

    // 不等于条件
    protected VectoRexConditionBuilder<T> ne(String fieldName, Object value) {
        filters.ne(fieldName, value);
        return this;
    }

    public VectoRexConditionBuilder<T> ne(FieldFunction<T, ?> fieldName, Object value) {
        String fn = getFieldName(fieldName);
        return ne(fn, value);
    }

    // 大于条件
    protected VectoRexConditionBuilder<T> gt(String fieldName, Comparable value) {
        filters.gt(fieldName, value);
        return this;
    }

    public VectoRexConditionBuilder<T> gt(FieldFunction<T, Comparable> fieldName, Comparable value) {
        String fn = getFieldName(fieldName);
        return gt(fn, value);
    }

    // 大于等于条件
    protected VectoRexConditionBuilder<T> ge(String fieldName, Comparable value) {
        filters.ge(fieldName, value);
        return this;
    }

    public VectoRexConditionBuilder<T> ge(FieldFunction<T, Comparable> fieldName, Comparable value) {
        String fn = getFieldName(fieldName);
        return ge(fn, value);
    }

    // 小于条件
    protected VectoRexConditionBuilder<T> lt(String fieldName, Comparable value) {
        filters.lt(fieldName, value);
        return this;
    }

    public VectoRexConditionBuilder<T> lt(FieldFunction<T, Comparable> fieldName, Comparable value) {
        String fn = getFieldName(fieldName);
        return lt(fn, value);
    }

    // 小于等于条件
    protected VectoRexConditionBuilder<T> le(String fieldName, Comparable value) {
        filters.le(fieldName, value);
        return this;
    }

    public VectoRexConditionBuilder<T> le(FieldFunction<T, Comparable> fieldName, Comparable value) {
        String fn = getFieldName(fieldName);
        return le(fn, value);
    }

    // 范围条件
    protected VectoRexConditionBuilder<T> between(String fieldName, Comparable start, Comparable end) {
        filters.between(fieldName, start, end);
        return this;
    }

    public VectoRexConditionBuilder<T> between(FieldFunction<T, Comparable> fieldName, Comparable start, Comparable end) {
        String fn = getFieldName(fieldName);
        return between(fn, start, end);
    }

    // IN 条件
    protected VectoRexConditionBuilder<T> in(String fieldName, List<?> values) {
        filters.in(fieldName, values);
        return this;
    }

    public VectoRexConditionBuilder<T> in(FieldFunction<T, ?> fieldName, List<?> values) {
        String fn = getFieldName(fieldName);
        return in(fn, values);
    }

    /**
     * 添加LIKE条件。
     *
     * @param fieldName 字段名
     * @param value     要匹配的模式
     * @return 当前条件构建器对象
     */
    protected VectoRexConditionBuilder<T> like(String fieldName, String value) {
        filters.like(fieldName, value);
        return this;
    }

    /**
     * 添加LIKE条件。
     *
     * @param fieldName 字段名
     * @param value     要匹配的模式
     * @return 当前条件构建器对象
     */
    public VectoRexConditionBuilder<T> like(FieldFunction<T, String> fieldName, String value) {
        String fn = getFieldName(fieldName);
        return like(fn, value);
    }

    // 抽象方法，用于获取字段名
    protected abstract String getFieldName(FieldFunction<T, ?> fieldName);
}
