package req

import "vectorex-client-go/entity"

type VectoRexCollectionReq struct {
	CollectionName string               `json:"collectionName"`
	VectorFields   []entity.VectorField `json:"vectorFileds"`
	ScalarFields   []entity.ScalarField `json:"scalarFields"`
}
