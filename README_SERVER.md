# VectorRex Server

## 一、概述

VectorRex Server 是一个高性能的向量检索服务，支持创建集合、添加数据、查询数据等功能。它提供了 RESTful API 接口，方便客户端进行交互。

## 二、安装

### 2.1 环境要求

- **Java**：需要安装 JDK 1.8 或更高版本。
- **Maven**：用于构建项目。

### 2.2 源码构建

1. 克隆项目：

   ```sh
   git clone https://github.com/javpower/vectorrex-server.git
   ```

2. 进入项目目录：

   ```sh
   cd vectorrex-server
   ```

3. 使用 Maven 构建项目：

   ```sh
   mvn clean install
   ```

4. 运行主类 `io.github.javpower.vectorexserver.VectorRexServerApplication` 启动服务：

   ```sh
   java -jar target/vectorrex-server.jar
   ```

### 2.3 Docker 部署

1. 拉取 Docker 镜像：

   ```sh
   docker pull javpower/vectorex-server:v1.0.0
   ```

2. 运行 Docker 容器：

   ```sh
   docker run -d -p 8080:8080 --name vectorrex-server javpower/vectorex-server:v1.0.0
   ```

   服务默认监听 `8080` 端口，可通过 `-p` 参数映射到宿主机的端口。

### 3 接口文档
 ```sh
   http://localhost:port/doc.html
```
