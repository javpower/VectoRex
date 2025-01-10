package entity

type KeyValue[K comparable, V any] struct {
	Key   K `json:"key"`
	Value V `json:"value"`
}
