package io.github.javpower.vectorexserver.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "分页返回结果")
public class PageResult<T> {

    @Schema(description = "当前页数据")
    private List<T> data;

    @Schema(description = "总记录数")
    private long totalRecords;

    @Schema(description = "总页数")
    private int totalPages;

    @Schema(description = "当前页码")
    private int pageIndex;

    @Schema(description = "每页大小")
    private int pageSize;

    public PageResult(List<T> data, long totalRecords, int pageIndex, int pageSize) {
        this.data = data;
        this.totalRecords = totalRecords;
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.totalPages = (int) Math.ceil((double) totalRecords / pageSize);
    }
}