package entity

type ScalarField struct {
	Name         string `json:"name"`
	IsPrimaryKey bool   `json:"isPrimaryKey"`
}
