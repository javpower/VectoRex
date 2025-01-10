package entity

type VectoRexEntity struct {
	CollectionName string                          `json:"collectionName"`
	VectorFields   []KeyValue[string, VectorField] `json:"vectorFileds"`
	ScalarFields   []KeyValue[string, ScalarField] `json:"scalarFields"`
}
