# **VectoRex**

**纯 Java 实现的高性能向量搜索引擎**

<div style="display: inline-block; border: 4px solid #ccc; border-radius: 10px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); margin: 10px; padding: 10px;">
  <img src="./image/logo.png" alt="VectoRex" style="border-radius: 10px;" />
</div>

## **简介**

**VectoRex** 是一个 **纯 Java 实现** 的高性能、可扩展的向量搜索引擎，专为现代 AI 和大数据应用设计。它结合了高效的向量索引（HNSW）和强大的标量索引（倒排索引、范围索引），支持复杂的混合查询（向量 + 标量），适用于推荐系统、图像搜索、自然语言处理等场景。

### 核心优势

- **纯 Java 实现**：无需依赖外部库，跨平台兼容，易于集成和部署。
- **高性能搜索**：基于 HNSW 算法，支持大规模向量的快速搜索。
- **混合查询**：支持向量搜索与标量过滤的无缝结合。
- **持久化存储**：内置高效的数据存储机制，确保数据安全。
- **易用性**：简洁的 API 设计，引包即用，快速集成到现有系统。

---

## **为什么选择 VectoRex？**

### **纯 Java 实现的优势**

- **跨平台**：无需担心操作系统兼容性问题，真正实现“一次编写，到处运行”。
- **易于调试和维护**：纯 Java 代码，便于调试、优化和扩展。
- **轻量级**：不依赖复杂的第三方库，核心功能全部由 Java 实现。

### **性能与功能兼备**

- **高性能**：基于 HNSW 算法，支持高效的近似最近邻搜索（ANN）。
- **多功能**：支持向量搜索、标量过滤、混合查询等多种场景。
- **可扩展**：模块化设计，易于扩展新的索引算法和存储后端。

---

## **核心特性**

- **高性能向量搜索**  
  基于 HNSW（Hierarchical Navigable Small World）算法，支持高效的近似最近邻搜索（ANN），适用于高维向量数据。

- **混合查询**  
  支持向量搜索与标量过滤的结合，例如：
  - 查找与某张图片最相似的图片，且图片标签为“风景”。
  - 查找与某段文本最相似的文档，且发布时间在最近一周内。

- **持久化存储**  
  内置高效的数据存储机制，支持大规模数据集的存储和快速恢复。

- **灵活的索引管理**
  - 标量索引：支持倒排索引、范围索引，适用于精确匹配、范围查询和模糊查询。
  - 向量索引：支持动态添加、删除和更新向量数据。


## **模块介绍**

- **详情见相应模块的文档**

### **vectorex-starter**

`vectorex-starter` 是一个用于简化 VectoRex 服务集成的 Spring Boot Starter。它提供了自动配置和便捷的 API，帮助开发者快速在 Spring Boot 应用中使用 VectoRex 功能。

- **安装**

  通过 Maven 引入 VectoRex：

  ```xml
  <dependency>
      <groupId>io.github.javpower</groupId>
      <artifactId>vectorex-starter</artifactId>
      <version>1.0.0</version>
  </dependency>
  ```


### **vectorex-server**

`vectorex-server` 是一个独立部署的检索服务，支持创建集合、添加数据、查询数据等功能。它提供了 RESTful API 接口，方便客户端进行交互。


### **vectorex-client**

`vectorex-client` 是一个用于与 VectoRex 服务进行交互的 Java 客户端 SDK。它提供了创建集合、添加数据、查询数据等功能，帮助开发者更便捷地在应用中集成 VectoRex 服务。

- **安装**

  通过 Maven 引入 VectoRex 客户端：

  ```xml
  <dependency>
      <groupId>io.github.javpower</groupId>
      <artifactId>vectorrex-client</artifactId>
      <version>1.0.0</version>
  </dependency>
  ```


## **性能 benchmark**

- **待补充**

---

## **应用场景**

- **推荐系统**  
  快速找到与用户兴趣最匹配的内容。

- **图像搜索**  
  基于图像特征的相似性搜索。

- **自然语言处理**  
  语义搜索、文本相似度计算。

- **生物信息学**  
  基因序列比对、蛋白质结构搜索。

---


---

## **贡献指南**

- **待补充**

---

## **许可证**

VectoRex 基于 **Apache License 2.0** 开源。详情请参阅 [LICENSE](LICENSE) 文件。

---

## **联系我们**

- **邮箱**：javpower@163.com
- **GitHub**：[https://github.com/javpower/vectorex](https://github.com/javpower/vectorex)

---