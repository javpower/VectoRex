package io.github.javpower.vectorexclient.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
//添加集合数据请求参数
public class CollectionDataAddReq implements Serializable {
    private static final long serialVersionUID = 1L;
    //数据，键值对形式
    private Map<String, Object> metadata;

    //集合名称
    private String collectionName;

}