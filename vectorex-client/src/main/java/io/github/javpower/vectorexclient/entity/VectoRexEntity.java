package io.github.javpower.vectorexclient.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class VectoRexEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    private String collectionName;
    private List<KeyValue<String, VectorFiled>> vectorFileds; // 矢量字段
    private List<KeyValue<String, ScalarField>> scalarFields;// 标量字段

}