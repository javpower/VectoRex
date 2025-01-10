package entity

type VectorField struct {
	Name       string     `json:"name"`
	MetricType MetricType `json:"metricType"`
	Dimensions int        `json:"dimensions"`
}
