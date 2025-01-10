# **VectorRexClient GO SDK**


**VectoRex Go SDK** 是一个用于与 VectorRex 服务进行交互的 GO 客户端 SDK。它提供了创建集合、添加数据、查询数据等功能，帮助开发者更便捷地在应用中集成 VectorRex 服务。

---


## **安装**

使用以下命令安装 SDK：

```bash
go get github.com/javpower/VectoRex/vectorex-client-go
```

---

## **快速开始**

### **1. 初始化客户端**

```go
package main

import (
	"fmt"
	"vectorex-client-go/client"
	"vectorex-client-go/entity"
	"vectorex-client-go/req"
)

func main() {
	// 初始化客户端
	client, err := client.NewVectorRexClient("https://ip:port", "admin", "123456")
	if err != nil {
		fmt.Println("Failed to create client:", err)
		return
	}
	defer client.Close()

	// 创建集合
	req := req.VectoRexCollectionReq{
		CollectionName: "test_collection",
		VectorFields: []entity.VectorField{
			{
				Name:       "vector",
				MetricType: entity.FloatCosineDistance,
				Dimensions: 128,
			},
		},
		ScalarFields: []entity.ScalarField{
			{
				Name:         "id",
				IsPrimaryKey: true,
			},
		},
	}

	resp, err := client.CreateCollection(req)
	if err != nil {
		fmt.Println("Failed to create collection:", err)
		return
	}
	fmt.Println("Create collection response:", resp)
}

```

---

### **2. 查询集合数据**

```go
// 查询集合数据
queryBuilder := req.NewQueryBuilder().
	SetCollectionName("test_collection").
	SetTopK(10)

results, err := client.QueryCollectionData(queryBuilder)
if err != nil {
	fmt.Println("Failed to query collection data:", err)
	return
}
fmt.Println("Query results:", results)
```

---

### **3. 分页查询集合数据**

```go
// 分页查询集合数据
queryBuilder := req.NewQueryBuilder().
	SetCollectionName("test_collection").
	SetPageIndex(1).
	SetPageSize(10)

pageResult, err := client.PageCollectionData(queryBuilder)
if err != nil {
	fmt.Println("Failed to page collection data:", err)
	return
}
fmt.Println("Page result:", pageResult)
```

---

## **API 文档**

### **1. 客户端初始化**

```go
func NewVectorRexClient(baseUri, username, password string) (*VectorRexClient, error)
```

- **参数**：
    - `baseUri`：VectoRex 服务的基础 URL。
    - `username`：登录用户名。
    - `password`：登录密码。
- **返回值**：
    - `*VectorRexClient`：客户端实例。
    - `error`：错误信息。

---

### **2. 创建集合**

```go
func (c *VectorRexClient) CreateCollection(req req.VectoRexCollectionReq) (*res.ServerResponse, error)
```

- **参数**：
    - `req`：创建集合的请求参数。
- **返回值**：
    - `*res.ServerResponse`：服务端响应。
    - `error`：错误信息。

---

### **3. 删除集合**

```go
func (c *VectorRexClient) DelCollection(collection string) (*res.ServerResponse, error)
```

- **参数**：
    - `collection`：集合名称。
- **返回值**：
    - `*res.ServerResponse`：服务端响应。
    - `error`：错误信息。

---

### **4. 获取所有集合**

```go
func (c *VectorRexClient) GetCollections() ([]entity.VectoRexEntity, error)
```

- **返回值**：
    - `[]entity.VectoRexEntity`：所有集合的列表。
    - `error`：错误信息。

---

### **5. 添加集合数据**

```go
func (c *VectorRexClient) AddCollectionData(req req.CollectionDataAddReq) (*res.ServerResponse, error)
```

- **参数**：
    - `req`：添加数据的请求参数。
- **返回值**：
    - `*res.ServerResponse`：服务端响应。
    - `error`：错误信息。

---

### **6. 查询集合数据**

```go
func (c *VectorRexClient) QueryCollectionData(queryBuilder *req.QueryBuilder) ([]res.VectorSearchResult, error)
```

- **参数**：
    - `queryBuilder`：查询构建器。
- **返回值**：
    - `[]res.VectorSearchResult`：查询结果。
    - `error`：错误信息。

---

### **7. 分页查询集合数据**

```go
func (c *VectorRexClient) PageCollectionData(queryBuilder *req.QueryBuilder) (*res.PageResult[res.VectorSearchResult], error)
```

- **参数**：
    - `queryBuilder`：查询构建器。
- **返回值**：
    - `*res.PageResult[res.VectorSearchResult]`：分页查询结果。
    - `error`：错误信息。

---