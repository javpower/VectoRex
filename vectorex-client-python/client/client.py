import time
from typing import List, Optional

import requests

from .exceptions import LoginError, RequestError
from .models import CollectionRequest, QueryBuilder


class VectorRexClient:
    def __init__(self, base_uri: str, username: str, password: str):
        """
        初始化客户端。

        :param base_uri: VectoRex 服务的基础 URL。
        :param username: 登录用户名。
        :param password: 登录密码。
        """
        self.base_uri = base_uri
        self.username = username
        self.password = password
        self.token = None
        self.token_expire_time = None
        self.login()

    def login(self):
        """登录并获取 token。"""
        url = f"{self.base_uri}/vectorex/login"
        login_req = {"username": self.username, "password": self.password}
        try:
            response = requests.post(url, json=login_req)
            response.raise_for_status()
            server_response = response.json()
            if server_response["status"] != 0:
                raise LoginError(f"Login failed: {server_response['msg']}")
            self.token = server_response["data"]
            self.token_expire_time = time.time() + 7200  # Token 有效期为 2 小时
        except requests.RequestException as e:
            raise LoginError(f"Login request failed: {e}")

    def _check_token(self):
        """检查 token 是否过期。"""
        if time.time() >= self.token_expire_time:
            self.login()

    def _execute_request(self, method: str, path: str, data: Optional[dict] = None) -> dict:
        """发送 HTTP 请求并解析响应。"""
        self._check_token()
        url = f"{self.base_uri}{path}"
        headers = {"Content-Type": "application/json", "token": self.token}
        try:
            response = requests.request(method, url, json=data, headers=headers)
            response.raise_for_status()
            return response.json()
        except requests.RequestException as e:
            raise RequestError(f"Request failed: {e}")

    def create_collection(self, collection_req: CollectionRequest) -> dict:
        """创建集合。"""
        return self._execute_request("POST", "/vectorex/add/collections", collection_req.dict())

    def delete_collection(self, collection_name: str) -> dict:
        """删除集合。"""
        return self._execute_request("DELETE", f"/vectorex/del/collections/{collection_name}")

    def get_collections(self) -> List[dict]:
        """获取所有集合。"""
        response = self._execute_request("GET", "/vectorex/get/collections")
        return response["data"]

    def add_collection_data(self, data_req: dict) -> dict:
        """添加集合数据。"""
        return self._execute_request("POST", "/vectorex/collections/data/add", data_req)

    def update_collection_data(self, data_req: dict) -> dict:
        """更新集合数据。"""
        return self._execute_request("PUT", "/vectorex/collections/data/update", data_req)

    def delete_collection_data(self, data_req: dict) -> dict:
        """删除集合数据。"""
        return self._execute_request("DELETE", "/vectorex/collections/data/del", data_req)

    def query_collection_data(self, query_builder: QueryBuilder) -> List[dict]:
        """查询集合数据。"""
        response = self._execute_request("POST", "/vectorex/collections/data/query", query_builder.dict())
        return response["data"]

    def page_collection_data(self, query_builder: QueryBuilder) -> dict:
        """分页查询集合数据。"""
        return self._execute_request("POST", "/vectorex/collections/data/page", query_builder.dict())