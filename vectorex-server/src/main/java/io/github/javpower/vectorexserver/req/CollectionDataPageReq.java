package io.github.javpower.vectorexserver.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Schema(description = "分页查询集合数据请求参数")
public class CollectionDataPageReq extends PageRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "集合名称", example = "my_collection")
    private String collectionName;

    @Schema(description = "标量过滤条件列表")
    private List<ConditionFiledReq> query;

    @Schema(description = "向量数据", example = "[0.1, 0.2, 0.3]")
    private List<Float> vector;

    @Schema(description = "向量字段名称", example = "vector_field")
    private String vectorFieldName;

    @Schema(description = "返回的最相似结果数量", example = "10")
    private Integer topK;
}