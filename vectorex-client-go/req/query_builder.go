package req

type QueryBuilder struct {
	CollectionName  string
	Query           []ConditionField
	Vector          []float32
	VectorFieldName string
	TopK            int
	PageIndex       int
	PageSize        int
}

func NewQueryBuilder() *QueryBuilder {
	return &QueryBuilder{}
}

func (b *QueryBuilder) SetCollectionName(collectionName string) *QueryBuilder {
	b.CollectionName = collectionName
	return b
}

func (b *QueryBuilder) SetQuery(query []ConditionField) *QueryBuilder {
	b.Query = query
	return b
}

func (b *QueryBuilder) SetVector(vector []float32) *QueryBuilder {
	b.Vector = vector
	return b
}

func (b *QueryBuilder) SetVectorFieldName(vectorFieldName string) *QueryBuilder {
	b.VectorFieldName = vectorFieldName
	return b
}

func (b *QueryBuilder) SetTopK(topK int) *QueryBuilder {
	b.TopK = topK
	return b
}

func (b *QueryBuilder) SetPageIndex(pageIndex int) *QueryBuilder {
	b.PageIndex = pageIndex
	return b
}

func (b *QueryBuilder) SetPageSize(pageSize int) *QueryBuilder {
	b.PageSize = pageSize
	return b
}

func (b *QueryBuilder) Build() CollectionDataPageReq {
	return CollectionDataPageReq{
		CollectionName:  b.CollectionName,
		Query:           b.Query,
		Vector:          b.Vector,
		VectorFieldName: b.VectorFieldName,
		TopK:            b.TopK,
		PageIndex:       b.PageIndex,
		PageSize:        b.PageSize,
	}
}
