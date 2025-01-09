package io.github.javpower.vectorexclient.req;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
//条件字段请求参数
public class ConditionFiledReq {

    //操作符
    private String operator;

    //字段名称
    private String field;

    //字段值
    private Object value;

    //范围查询起始值
    private Comparable start;

    //范围查询结束值
    private Comparable end;

    //嵌套条件字段
    private List<ConditionFiledReq> conditionFiledReq=new ArrayList<>();
}