package req

type CollectionDataAddReq struct {
	CollectionName string                 `json:"collectionName"`
	Metadata       map[string]interface{} `json:"metadata"`
}

type CollectionDataDelReq struct {
	CollectionName string `json:"collectionName"`
	ID             string `json:"id"`
}

type CollectionDataQueryReq struct {
	CollectionName  string           `json:"collectionName"`
	Query           []ConditionField `json:"query"`
	Vector          []float32        `json:"vector"`
	VectorFieldName string           `json:"vectorFieldName"`
	TopK            int              `json:"topK"`
}

type CollectionDataPageReq struct {
	CollectionName  string           `json:"collectionName"`
	Query           []ConditionField `json:"query"`
	Vector          []float32        `json:"vector"`
	VectorFieldName string           `json:"vectorFieldName"`
	TopK            int              `json:"topK"`
	PageIndex       int              `json:"pageIndex"`
	PageSize        int              `json:"pageSize"`
}
