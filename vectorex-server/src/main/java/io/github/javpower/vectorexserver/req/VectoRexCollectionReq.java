package io.github.javpower.vectorexserver.req;

import io.github.javpower.vectorex.keynote.model.VectorFiled;
import io.github.javpower.vectorexcore.entity.ScalarField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Schema(description = "创建集合请求参数")
public class VectoRexCollectionReq implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "集合名称", example = "my_collection")
    private String collectionName;

    @Schema(description = "向量字段列表")
    private List<VectorFiled> vectorFileds;

    @Schema(description = "标量字段列表")
    private List<ScalarField> scalarFields;
}