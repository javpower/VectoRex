package io.github.javpower.vectorexserver.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "条件字段请求参数")
public class ConditionFiledReq {

    @Schema(description = "操作符", example = "eq")
    private String operator;

    @Schema(description = "字段名称", example = "age")
    private String field;

    @Schema(description = "字段值", example = "30")
    private Object value;

    @Schema(description = "范围查询起始值", example = "20")
    private Comparable start;

    @Schema(description = "范围查询结束值", example = "40")
    private Comparable end;

    @Schema(description = "嵌套条件字段")
    private List<ConditionFiledReq> conditionFiledReq;
}