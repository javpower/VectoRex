package io.github.javpower.vectorexclient.req;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
//分页查询集合数据请求参数
public class CollectionDataPageReq extends PageRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    //集合名称
    private String collectionName;

    //标量过滤条件列表
    private List<ConditionFiledReq> query;

    //向量数据
    private List<Float> vector;

    //向量字段名称
    private String vectorFieldName;

    //返回的最相似结果数量
    private Integer topK;
}