package io.github.javpower.vectorexbootstater.builder;

import io.github.javpower.vectorex.keynote.query.ConditionBuilder;
import io.github.javpower.vectorexbootstater.core.FieldFunction;

import java.util.List;

/**
 * 条件构建器抽象基类，用于构建条件。
 */
public abstract class VectoRexConditionBuilder<T> {

    protected ConditionBuilder filters = new ConditionBuilder();

    /**
     * 添加等于条件。
     *
     * @param fieldName 字段名
     * @param value     要比较的值
     * @return 当前条件构建器对象
     */
    protected VectoRexConditionBuilder<T> eq(String fieldName, Object value) {
        filters.eq(fieldName, value);
        return this;
    }

    /**
     * 添加等于条件。
     *
     * @param fieldName 字段名
     * @param values    要比较的值列表
     * @return 当前条件构建器对象
     */
    public VectoRexConditionBuilder<T> eq(FieldFunction<T, ?> fieldName, List<?> values) {
        String fn = getFieldName(fieldName);
        return eq(fn, values);
    }

    /**
     * 添加不等于条件。
     *
     * @param fieldName 字段名
     * @param value     要比较的值
     * @return 当前条件构建器对象
     */
    protected VectoRexConditionBuilder<T> ne(String fieldName, Object value) {
        filters.ne(fieldName, value);
        return this;
    }

    /**
     * 添加不等于条件。
     *
     * @param fieldName 字段名
     * @param values    要比较的值列表
     * @return 当前条件构建器对象
     */
    public VectoRexConditionBuilder<T> ne(FieldFunction<T, ?> fieldName, List<?> values) {
        String fn = getFieldName(fieldName);
        return ne(fn, values);
    }

    /**
     * 添加大于条件。
     *
     * @param fieldName 字段名
     * @param value     要比较的值
     * @return 当前条件构建器对象
     */
    protected VectoRexConditionBuilder<T> gt(String fieldName, Comparable value) {
        filters.gt(fieldName, value);
        return this;
    }

    /**
     * 添加大于条件。
     *
     * @param fieldName 字段名
     * @param value     要比较的值
     * @return 当前条件构建器对象
     */
    public VectoRexConditionBuilder<T> gt(FieldFunction<T, Comparable> fieldName, Comparable value) {
        String fn = getFieldName(fieldName);
        return gt(fn, value);
    }

    /**
     * 添加大于等于条件。
     *
     * @param fieldName 字段名
     * @param value     要比较的值
     * @return 当前条件构建器对象
     */
    protected VectoRexConditionBuilder<T> ge(String fieldName, Comparable value) {
        filters.ge(fieldName, value);
        return this;
    }

    /**
     * 添加大于等于条件。
     *
     * @param fieldName 字段名
     * @param value     要比较的值
     * @return 当前条件构建器对象
     */
    public VectoRexConditionBuilder<T> ge(FieldFunction<T, Comparable> fieldName, Comparable value) {
        String fn = getFieldName(fieldName);
        return ge(fn, value);
    }

    /**
     * 添加小于条件。
     *
     * @param fieldName 字段名
     * @param value     要比较的值
     * @return 当前条件构建器对象
     */
    protected VectoRexConditionBuilder<T> lt(String fieldName, Comparable value) {
        filters.lt(fieldName, value);
        return this;
    }

    /**
     * 添加小于条件。
     *
     * @param fieldName 字段名
     * @param value     要比较的值
     * @return 当前条件构建器对象
     */
    public VectoRexConditionBuilder<T> lt(FieldFunction<T, Comparable> fieldName, Comparable value) {
        String fn = getFieldName(fieldName);
        return lt(fn, value);
    }

    /**
     * 添加小于等于条件。
     *
     * @param fieldName 字段名
     * @param value     要比较的值
     * @return 当前条件构建器对象
     */
    protected VectoRexConditionBuilder<T> le(String fieldName, Comparable value) {
        filters.le(fieldName, value);
        return this;
    }

    /**
     * 添加小于等于条件。
     *
     * @param fieldName 字段名
     * @param value     要比较的值
     * @return 当前条件构建器对象
     */
    public VectoRexConditionBuilder<T> le(FieldFunction<T, Comparable> fieldName, Comparable value) {
        String fn = getFieldName(fieldName);
        return le(fn, value);
    }

    /**
     * 添加范围条件。
     *
     * @param fieldName 字段名
     * @param start     范围开始值
     * @param end       范围结束值
     * @return 当前条件构建器对象
     */
    protected VectoRexConditionBuilder<T> between(String fieldName, Comparable start, Comparable end) {
        filters.between(fieldName, start, end);
        return this;
    }

    /**
     * 添加范围条件。
     *
     * @param fieldName 字段名
     * @param start     范围开始值
     * @param end       范围结束值
     * @return 当前条件构建器对象
     */
    public VectoRexConditionBuilder<T> between(FieldFunction<T, Comparable> fieldName, Comparable start, Comparable end) {
        String fn = getFieldName(fieldName);
        return between(fn, start, end);
    }

    /**
     * 添加IN条件。
     *
     * @param fieldName 字段名
     * @param values    要检查的值列表
     * @return 当前条件构建器对象
     */
    protected VectoRexConditionBuilder<T> in(String fieldName, List<?> values) {
        filters.in(fieldName, values);
        return this;
    }

    /**
     * 添加IN条件。
     *
     * @param fieldName 字段名
     * @param values    要检查的值列表
     * @return 当前条件构建器对象
     */
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

    private String getFieldName(FieldFunction<T, ?> fieldFunction) {
        return fieldFunction.getFieldName(fieldFunction);
    }
}