package res

// PageResult 是一个分页结果结构体
type PageResult[T any] struct {
	Data         []T   `json:"data"`         // 当前页数据
	TotalRecords int64 `json:"totalRecords"` // 总记录数
	TotalPages   int   `json:"totalPages"`   // 总页数
	PageIndex    int   `json:"pageIndex"`    // 当前页码
	PageSize     int   `json:"pageSize"`     // 每页大小
}
