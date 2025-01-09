package io.github.javpower.vectorexclient.res;

import lombok.Data;

import java.util.List;

@Data
//分页返回结果
public class PageResult<T> {

    //当前页数据
    private List<T> data;

    //总记录数
    private long totalRecords;

    //总页数
    private int totalPages;

    //当前页码
    private int pageIndex;

    //每页大小
    private int pageSize;

    public PageResult(List<T> data, long totalRecords, int pageIndex, int pageSize) {
        this.data = data;
        this.totalRecords = totalRecords;
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.totalPages = (int) Math.ceil((double) totalRecords / pageSize);
    }
}