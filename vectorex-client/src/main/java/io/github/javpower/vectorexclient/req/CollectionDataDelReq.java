package io.github.javpower.vectorexclient.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
//删除集合数据请求参数
public class CollectionDataDelReq implements Serializable {
    private static final long serialVersionUID = 1L;
    //集合名称
    private String collectionName;
    //数据唯一标识
    private String id;
}