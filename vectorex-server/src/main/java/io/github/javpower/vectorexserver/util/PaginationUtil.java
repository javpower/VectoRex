package io.github.javpower.vectorexserver.util;

import io.github.javpower.vectorexserver.response.PageResult;

import java.util.Collections;
import java.util.List;

/**
 * 分页工具类
 */
public class PaginationUtil {

    /**
     * 对数据进行分页
     *
     * @param dataList   所有数据
     * @param pageIndex  页码（从 1 开始）
     * @param pageSize   每页大小
     * @param <T>        数据类型
     * @return 分页结果
     */
    public static <T> PageResult<T> paginate(List<T> dataList, int pageIndex, int pageSize) {
        if (dataList == null || pageIndex < 1 || pageSize <= 0) {
            throw new IllegalArgumentException("Invalid input parameters");
        }

        int totalRecords = dataList.size();
//        int totalPages = (int) Math.ceil((double) totalRecords / pageSize);

        // 计算分页范围
        int fromIndex = (pageIndex - 1) * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, totalRecords);

        // 获取当前页数据
        List<T> pageData = (fromIndex >= totalRecords) 
            ? Collections.emptyList() 
            : dataList.subList(fromIndex, toIndex);

        return new PageResult<>(pageData, totalRecords, pageIndex, pageSize);
    }

}