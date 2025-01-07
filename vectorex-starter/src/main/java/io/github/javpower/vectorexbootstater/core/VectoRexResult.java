package io.github.javpower.vectorexbootstater.core;

import lombok.Data;

/**
 * @author xgc
 **/
@Data
public class VectoRexResult<T> {
    private T entity;
    private Float score;
}
