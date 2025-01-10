package client

type ServerResponse struct {
	Status int         `json:"status"`
	Msg    string      `json:"msg"`
	Data   interface{} `json:"data"`
}

func (r *ServerResponse) IsSuccess() bool {
	return r.Status == 0
}
