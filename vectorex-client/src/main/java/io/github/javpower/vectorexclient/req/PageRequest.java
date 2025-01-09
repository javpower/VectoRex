package io.github.javpower.vectorexclient.req;

import lombok.Data;

@Data
public class PageRequest {

    //页码
    private Integer pageIndex = 1;

    //页大小,默认为10
    private Integer pageSize = 10;
}