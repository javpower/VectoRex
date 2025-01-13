package io.github.javpower.vectorexserver.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(description = "创建集合请求参数")
public class VectoRexCollectionPageReq extends PageRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "集合名称", example = "my_collection")
    private String collectionName;


}