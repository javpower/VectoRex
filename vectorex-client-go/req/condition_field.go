package req

type ConditionField struct {
	Operator   string           `json:"operator"`
	Field      string           `json:"field"`
	Value      interface{}      `json:"value"`
	Start      interface{}      `json:"start"`
	End        interface{}      `json:"end"`
	Conditions []ConditionField `json:"conditions"`
}
