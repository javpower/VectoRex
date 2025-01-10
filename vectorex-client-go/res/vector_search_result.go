package res

type VectorSearchResult struct {
	ID    string  `json:"id"`
	Score float32 `json:"score"`
	Data  DbData  `json:"data"`
}
