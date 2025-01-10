package res

type ResponseCode struct {
	Code int
	Desc string
}

var (
	Success = ResponseCode{Code: 0, Desc: "SUCCESS"}
	Error   = ResponseCode{Code: 1, Desc: "ERROR"}
	NoAuth  = ResponseCode{Code: 401, Desc: "NO_AUTH"}
)
