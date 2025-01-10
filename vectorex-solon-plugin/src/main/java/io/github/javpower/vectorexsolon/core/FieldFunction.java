package io.github.javpower.vectorexsolon.core;


import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;
import java.util.Optional;
import java.util.function.Function;

/**
 * Lambda 字段函数接口
 *
 * @author cxc
 */
@FunctionalInterface
public interface  FieldFunction<T, R> extends Function<T, R>, Serializable {

    // 默认分隔符
    String DEFAULT_SPLIT = "";

    // 默认转换类型：0-不转换，1-大写，2-小写
    int DEFAULT_TO_TYPE = 0;

    /**
     * 获取实体类的字段名称（带分隔符和转换类型）
     * @param fn     字段函数
     * @param split  分隔符
     * @param toType 转换类型
     * @return 转换后的字段名
     */
    default String getFieldName(FieldFunction<T, ?> fn, String split, int toType) {
        SerializedLambda lambda = getSerializedLambda(fn);
        String implClass = lambda.getImplClass().replace("/", ".");
        String implMethodName = lambda.getImplMethodName();
        String fieldName = extractFieldName(implClass, implMethodName);
        return transformFieldName(fieldName, split, toType);
    }

    /**
     * 获取实体类的字段名称（默认无分隔符，不转换）
     * @param fn 字段函数
     * @return 字段名
     */
    default String getFieldName(FieldFunction<T, ?> fn) {
        return getFieldName(fn, DEFAULT_SPLIT, DEFAULT_TO_TYPE);
    }

    /**
     * 获取给定函数对象的序列化lambda表达式
     * @param function 要序列化的函数对象
     * @return 序列化的lambda表达式
     */
    default SerializedLambda getSerializedLambda(FieldFunction<T, ?> function) {
        return Optional.ofNullable(function)
                .map(this::extractSerializedLambda)
                .orElseThrow(() -> new RuntimeException("传入的函数对象为null或没有writeReplace方法"));
    }

    /**
     * 提取序列化的Lambda
     * @param function 字段函数
     * @return 序列化的Lambda
     */
    default SerializedLambda extractSerializedLambda(FieldFunction<T, ?> function) {
        try {
            Method writeReplace = function.getClass().getDeclaredMethod("writeReplace");
            writeReplace.setAccessible(true);
            return (SerializedLambda) writeReplace.invoke(function);
        } catch (Exception e) {
            throw new RuntimeException("无法获取SerializedLambda", e);
        }
    }

    /**
     * 提取字段名称
     * @param implClass 实现类
     * @param implMethodName 实现方法名
     * @return 字段名
     */
    default String extractFieldName(String implClass, String implMethodName) {
        try {
            Class<?> clazz = Class.forName(implClass);
            return methodToFieldName(implMethodName);
        } catch (Exception e) {
            throw new RuntimeException("无法提取字段名称", e);
        }
    }

    /**
     * 方法名转换为字段名
     * @param methodName 方法名
     * @return 字段名
     */
    default String methodToFieldName(String methodName) {
        if (methodName.startsWith("get")) {
            return Character.toLowerCase(methodName.charAt(3)) + methodName.substring(4);
        } else if (methodName.startsWith("is")) {
            return Character.toLowerCase(methodName.charAt(2)) + methodName.substring(3);
        }
        return methodName;
    }

    /**
     * 转换字段名
     * @param fieldName 原始字段名
     * @param split 分隔符
     * @param toType 转换类型
     * @return 转换后的字段名
     */
    default String transformFieldName(String fieldName, String split, int toType) {
        String result = fieldName;

        // 处理分隔符
        if (split != null && !split.isEmpty()) {
            result = result.replaceAll("([A-Z])", split + "$1").toLowerCase();
        }

        // 处理大小写转换
        switch (toType) {
            case 1: // 大写
                result = result.toUpperCase();
                break;
            case 2: // 小写
                result = result.toLowerCase();
                break;
            default: // 保持原样
                break;
        }

        return result;
    }

}
