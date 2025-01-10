package entity

type MetricType string

const (
	FloatCosineDistance      MetricType = "FLOAT_COSINE_DISTANCE"
	FloatInnerProduct        MetricType = "FLOAT_INNER_PRODUCT"
	FloatEuclideanDistance   MetricType = "FLOAT_EUCLIDEAN_DISTANCE"
	FloatCanberraDistance    MetricType = "FLOAT_CANBERRA_DISTANCE"
	FloatBrayCurtisDistance  MetricType = "FLOAT_BRAY_CURTIS_DISTANCE"
	FloatCorrelationDistance MetricType = "FLOAT_CORRELATION_DISTANCE"
	FloatManhattanDistance   MetricType = "FLOAT_MANHATTAN_DISTANCE"
)
