package io.github.javpower.vectorexserver.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(description = "删除集合数据请求参数")
public class CollectionDataDelReq implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "集合名称", example = "my_collection")
    private String collectionName;

    @Schema(description = "数据唯一标识", example = "12345")
    private String id;
}