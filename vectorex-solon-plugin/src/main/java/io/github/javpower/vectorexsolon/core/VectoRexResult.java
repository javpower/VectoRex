package io.github.javpower.vectorexsolon.core;

import lombok.Data;

/**
 * @author cxc
 */
@Data
public class VectoRexResult<T> {
    /**
     * 实体对象
     */
    private T entity;

    /**
     * 相似度得分
     */
    private Float score;

    /**
     * 创建成功的结果
     * @param entity 实体
     * @param score 得分
     * @param <T> 实体类型
     * @return 结果对象
     */
    public static <T> VectoRexResult<T> success(T entity, Float score) {
        VectoRexResult<T> result = new VectoRexResult<>();
        result.setEntity(entity);
        result.setScore(score);
        return result;
    }

    /**
     * 创建空结果
     * @param <T> 实体类型
     * @return 空结果对象
     */
    public static <T> VectoRexResult<T> empty() {
        return new VectoRexResult<>();
    }
}
