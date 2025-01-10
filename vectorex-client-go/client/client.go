package client

import (
	"bytes"
	"encoding/json"
	"fmt"
	"io"
	"net/http"
	"time"
	"vectorex-client-go/entity"
	"vectorex-client-go/req"
	"vectorex-client-go/res"
)

const (
	TokenExpireTime = 2*time.Hour - time.Minute // Token 过期时间
)

type VectorRexClient struct {
	baseUri         string
	username        string
	password        string
	httpClient      *http.Client
	token           string
	tokenExpireTime time.Time
}

// NewVectorRexClient 创建一个新的 VectorRexClient 实例
func NewVectorRexClient(baseUri, username, password string) (*VectorRexClient, error) {
	client := &VectorRexClient{
		baseUri:    baseUri,
		username:   username,
		password:   password,
		httpClient: &http.Client{},
	}
	if err := client.login(); err != nil {
		return nil, err
	}
	return client, nil
}

// login 登录并获取 token
func (c *VectorRexClient) login() error {
	url := c.baseUri + "/vectorex/login"
	loginReq := map[string]string{
		"username": c.username,
		"password": c.password,
	}

	// 将登录请求体转换为 JSON 字符串并打印
	jsonBody, err := json.MarshalIndent(loginReq, "", "  ")
	if err != nil {
		return fmt.Errorf("failed to marshal login request body: %v", err)
	}
	fmt.Println("Login Request JSON Data:")
	fmt.Println(string(jsonBody))

	// 发送登录请求
	resp, err := c.httpClient.Post(url, "application/json", bytes.NewBuffer(jsonBody))
	if err != nil {
		return fmt.Errorf("failed to send login request: %v", err)
	}
	defer resp.Body.Close()

	// 打印服务端响应状态
	fmt.Println("Login Response Status:", resp.Status)

	// 解析服务端响应
	var serverResponse res.ServerResponse
	if err := json.NewDecoder(resp.Body).Decode(&serverResponse); err != nil {
		return fmt.Errorf("failed to decode login response: %v", err)
	}
	fmt.Println("Login Response Body:", serverResponse)

	// 检查登录是否成功
	if !serverResponse.IsSuccess() {
		return fmt.Errorf("login failed: %s", serverResponse.Msg)
	}

	// 获取 token 并设置过期时间
	c.token = serverResponse.Data.(string)
	c.tokenExpireTime = time.Now().Add(TokenExpireTime)
	fmt.Println("Login successful, token:", c.token)
	return nil
}

// checkToken 检查 token 是否过期
func (c *VectorRexClient) checkToken() error {
	if time.Now().After(c.tokenExpireTime) {
		return c.login()
	}
	return nil
}

// executeRequest 发送 HTTP 请求并解析响应
func (c *VectorRexClient) executeRequest(method, path string, reqBody interface{}, dataType interface{}) (*res.ServerResponse, error) {
	if err := c.checkToken(); err != nil {
		return nil, err
	}

	url := c.baseUri + path
	var body io.Reader
	if reqBody != nil {
		// 将请求体转换为 JSON 字符串并打印
		jsonBody, err := json.MarshalIndent(reqBody, "", "  ")
		if err != nil {
			return nil, fmt.Errorf("failed to marshal request body: %v", err)
		}
		fmt.Println("Request JSON Data:")
		fmt.Println(string(jsonBody))
		body = bytes.NewBuffer(jsonBody)
	}

	req, err := http.NewRequest(method, url, body)
	if err != nil {
		return nil, err
	}
	req.Header.Set("Content-Type", "application/json")
	req.Header.Set("token", c.token)

	resp, err := c.httpClient.Do(req)
	if err != nil {
		return nil, err
	}
	defer resp.Body.Close()

	var serverResponse res.ServerResponse
	if err := json.NewDecoder(resp.Body).Decode(&serverResponse); err != nil {
		return nil, err
	}
	fmt.Println("Response Status:", resp.Status)
	fmt.Println("Response Body:", serverResponse)

	// 解析 Data 字段
	if dataType != nil {
		if err := serverResponse.GetData(dataType); err != nil {
			return nil, err
		}
	}

	return &serverResponse, nil
}

// CreateCollection 创建集合
func (c *VectorRexClient) CreateCollection(req req.VectoRexCollectionReq) (*res.ServerResponse, error) {
	return c.executeRequest("POST", "/vectorex/add/collections", req, nil)
}

// DelCollection 删除集合
func (c *VectorRexClient) DelCollection(collection string) (*res.ServerResponse, error) {
	return c.executeRequest("DELETE", "/vectorex/del/collections/"+collection, nil, nil)
}

// GetCollections 获取所有集合
func (c *VectorRexClient) GetCollections() ([]entity.VectoRexEntity, error) {
	var collections []entity.VectoRexEntity
	_, err := c.executeRequest("GET", "/vectorex/get/collections", nil, &collections)
	if err != nil {
		return nil, err
	}
	return collections, nil
}

// AddCollectionData 添加集合数据
func (c *VectorRexClient) AddCollectionData(req req.CollectionDataAddReq) (*res.ServerResponse, error) {
	return c.executeRequest("POST", "/vectorex/collections/data/add", req, nil)
}

// UpdateCollectionData 更新集合数据
func (c *VectorRexClient) UpdateCollectionData(req req.CollectionDataAddReq) (*res.ServerResponse, error) {
	return c.executeRequest("PUT", "/vectorex/collections/data/update", req, nil)
}

// DeleteCollectionData 删除集合数据
func (c *VectorRexClient) DeleteCollectionData(req req.CollectionDataDelReq) (*res.ServerResponse, error) {
	return c.executeRequest("DELETE", "/vectorex/collections/data/del", req, nil)
}

// QueryCollectionData 查询集合数据
func (c *VectorRexClient) QueryCollectionData(queryBuilder *req.QueryBuilder) ([]res.VectorSearchResult, error) {
	queryReq := queryBuilder.Build()
	req := req.CollectionDataQueryReq{
		CollectionName:  queryReq.CollectionName,
		Query:           queryReq.Query,
		Vector:          queryReq.Vector,
		VectorFieldName: queryReq.VectorFieldName,
		TopK:            queryReq.TopK,
	}

	var results []res.VectorSearchResult
	_, err := c.executeRequest("POST", "/vectorex/collections/data/query", req, &results)
	if err != nil {
		return nil, err
	}
	return results, nil
}

// PageCollectionData 分页查询集合数据
func (c *VectorRexClient) PageCollectionData(queryBuilder *req.QueryBuilder) (*res.PageResult[res.VectorSearchResult], error) {
	queryReq := queryBuilder.Build()

	var pageResult res.PageResult[res.VectorSearchResult]
	_, err := c.executeRequest("POST", "/vectorex/collections/data/page", queryReq, &pageResult)
	if err != nil {
		return nil, err
	}
	return &pageResult, nil
}

// Close 关闭客户端
func (c *VectorRexClient) Close() {
	// 清理资源
}
