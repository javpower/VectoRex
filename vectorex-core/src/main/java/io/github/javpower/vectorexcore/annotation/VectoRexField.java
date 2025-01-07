package io.github.javpower.vectorexcore.annotation;

import io.github.javpower.vectorex.keynote.model.MetricType;
import io.github.javpower.vectorexcore.entity.DataType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author xgc
 **/
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface VectoRexField {


    /**
     * 字段名称，默认使用 Java 字段名
     */
    String name() default "";

    /**
     * 数据类型，默认为 FLOAT_VECTOR
     *
     * @see DataType
     */
    DataType dataType() default DataType.Scalar;

    /**
     * 向量维度，仅对向量类型有效
     */
    int dimension() default -1;

    /**
     * 度量类型，仅对向量类型有效
     */
    MetricType metricType() default MetricType.FLOAT_COSINE_DISTANCE;

    /**
     * 是否为主键
     */
    boolean isPrimaryKey() default false;

}