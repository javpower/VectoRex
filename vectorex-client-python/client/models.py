from typing import List, Dict, Optional

from pydantic import BaseModel


class VectorField(BaseModel):
    """向量字段模型。"""
    name: str
    metricType: str
    dimensions: int

class ScalarField(BaseModel):
    """标量字段模型。"""
    name: str
    isPrimaryKey: bool

class CollectionRequest(BaseModel):
    """创建集合请求模型。"""
    collectionName: str
    vectorFileds: List[VectorField]
    scalarFields: List[ScalarField]

class QueryBuilder(BaseModel):
    """查询构建器模型。"""
    collection_name: str
    query: Optional[List[Dict]] = None
    vector: Optional[List[float]] = None
    vectorFieldName: Optional[str] = None
    topK: Optional[int] = None
    pageIndex: Optional[int] = None
    pageSize: Optional[int] = None