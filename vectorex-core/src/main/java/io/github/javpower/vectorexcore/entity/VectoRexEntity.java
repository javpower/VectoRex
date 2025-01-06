package io.github.javpower.vectorexcore.entity;

import io.github.javpower.vectorex.keynote.model.VectorFiled;
import lombok.Data;

import java.util.List;

/**
 * @author xgc
 **/
@Data
public class VectoRexEntity {
    private String collectionName;
    private List<KeyValue<String, VectorFiled>> vectorFileds; // 矢量字段
    private List<KeyValue<String, ScalarField>> scalarFields;// 标量字段

}
