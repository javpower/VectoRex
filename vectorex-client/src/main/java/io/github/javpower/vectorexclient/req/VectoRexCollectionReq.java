package io.github.javpower.vectorexclient.req;

import io.github.javpower.vectorexclient.entity.ScalarField;
import io.github.javpower.vectorexclient.entity.VectorFiled;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
//创建集合请求参数
public class VectoRexCollectionReq implements Serializable {
    private static final long serialVersionUID = 1L;

    //集合名称
    private String collectionName;

    //向量字段列表
    private List<VectorFiled> vectorFileds;

    //标量字段列表
    private List<ScalarField> scalarFields;

}