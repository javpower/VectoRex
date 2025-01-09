package io.github.javpower.vectorexserver.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class PageRequest {

    @Schema(description = "页码,默认为1", example = "1")
    private Integer pageIndex = 1;

    @Schema(description = "页大小,默认为10", example = "10")
    private Integer pageSize = 10;
}