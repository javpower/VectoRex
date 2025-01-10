package vectorex_client_go

//func main() {
//	// 初始化客户端
//	client, err := client.NewVectorRexClient("https://vectorex.m78cloud.cn", "admin", "123456")
//	if err != nil {
//		fmt.Println("Failed to create client:", err)
//		return
//	}
//	defer client.Close()
//
//	// 创建集合
//	req := req.VectoRexCollectionReq{
//		CollectionName: "test_collection",
//		VectorFields: []entity.VectorField{
//			{
//				Name:       "vector",
//				MetricType: entity.FloatCosineDistance,
//				Dimensions: 128,
//			},
//		},
//		ScalarFields: []entity.ScalarField{
//			{
//				Name:         "id",
//				IsPrimaryKey: true,
//			},
//		},
//	}
//
//	resp, err := client.CreateCollection(req)
//	if err != nil {
//		fmt.Println("Failed to create collection:", err)
//		return
//	}
//	fmt.Println("Create collection response:", resp)
//}
