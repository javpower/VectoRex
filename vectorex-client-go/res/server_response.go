package res

import (
	"encoding/json"
	"fmt"
)

// ServerResponse 是一个通用的响应结构体
type ServerResponse struct {
	Status int         `json:"status"` // 状态码
	Msg    string      `json:"msg"`    // 消息
	Data   interface{} `json:"data"`   // 数据
}

// IsSuccess 检查响应是否成功
func (r *ServerResponse) IsSuccess() bool {
	return r.Status == 0
}

// GetData 获取 Data 字段的值，并将其转换为指定类型
func (r *ServerResponse) GetData(dataType interface{}) error {
	// 将 Data 字段的值转换为 JSON 字节
	jsonData, err := json.Marshal(r.Data)
	if err != nil {
		return fmt.Errorf("failed to marshal data: %v", err)
	}

	// 将 JSON 字节解析为指定类型
	if err := json.Unmarshal(jsonData, dataType); err != nil {
		return fmt.Errorf("failed to unmarshal data: %v", err)
	}

	return nil
}
