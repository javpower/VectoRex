#
#
# import os
# import sys
#
# from client import VectorRexClient, CollectionRequest, VectorField, ScalarField
#
# # 添加项目根目录到 Python 路径
# sys.path.append(os.path.dirname(os.path.dirname(os.path.abspath(__file__))))
#
# def main():
#     # 初始化客户端
#     client = VectorRexClient(base_uri="https://vectorex.m78cloud.cn", username="admin", password="123456")
#
#     # 创建集合
#     collection_req = CollectionRequest(
#         collectionName="test_collection",
#         vectorFileds=[VectorField(name="vector", metricType="FLOAT_COSINE_DISTANCE", dimensions=128)],
#         scalarFields=[ScalarField(name="id", isPrimaryKey=True)]
#     )
#     response = client.create_collection(collection_req)
#     if response["status"] == 0:
#         print("Collection created successfully!")
#     else:
#         print(f"Failed to create collection: {response['msg']}")
#
# if __name__ == "__main__":
#     main()