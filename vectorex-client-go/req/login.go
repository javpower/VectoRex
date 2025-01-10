package req

type LoginUser struct {
	Username string `json:"username"`
	Password string `json:"password"`
}
