package entity

type VectoRexEntity struct {
	CollectionName string                          `json:"collectionName"`
	VectorFields   []KeyValue[string, VectorField] `json:"vectorFields"`
	ScalarFields   []KeyValue[string, ScalarField] `json:"scalarFields"`
}
