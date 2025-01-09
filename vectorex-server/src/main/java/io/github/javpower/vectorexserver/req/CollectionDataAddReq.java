package io.github.javpower.vectorexserver.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
@Schema(description = "添加集合数据请求参数")
public class CollectionDataAddReq implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "数据，键值对形式", example = "{\"key1\": \"value1\", \"key2\": \"value2\"}")
    private Map<String, Object> metadata;

    @Schema(description = "集合名称", example = "my_collection")
    private String collectionName;

}