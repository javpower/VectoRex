# VectorRexClient SDK

## 一、概述

VectorRexClient 是一个用于与 VectorRex 服务进行交互的 Java 客户端 SDK。它提供了创建集合、添加数据、查询数据等功能，帮助开发者更便捷地在应用中集成 VectorRex 服务。

## 二、安装

### 2.1 依赖添加

在项目的 `pom.xml` 文件中添加以下依赖：

```xml
<dependency>
    <groupId>io.github.javpower</groupId>
    <artifactId>vectorrex-client</artifactId>
    <version>1.0.0</version>
</dependency>
```

### 2.2 初始化

```java
VectorRexClient client = new VectorRexClient("http://your-vectorrex-service-base-uri", "username", "password");
```

## 三、功能模块

### 3.1 集合管理

#### 3.1.1 创建集合

创建一个新的集合，需要指定集合名称、向量字段列表和标量字段列表。

```java
        List<ScalarField> scalarFields = new ArrayList();
        ScalarField id = ScalarField.builder().name("id").isPrimaryKey(true).build();
        ScalarField name = ScalarField.builder().name("name").isPrimaryKey(false).build();
        scalarFields.add(id);
        scalarFields.add(name);
        List<VectorFiled> vectorFileds = new ArrayList();
        VectorFiled vector = VectorFiled.builder().name("vector").metricType(MetricType.FLOAT_CANBERRA_DISTANCE).dimensions(3).build();
        vectorFileds.add(vector);
        ServerResponse<Void> face = client.createCollection(VectoRexCollectionReq.builder().collectionName("face").scalarFields(scalarFields).vectorFileds(vectorFileds).build());
```

#### 3.1.2 删除集合

根据集合名称删除一个集合。

```java
ServerResponse<Void> response = client.delCollection("your-collection-name");
```

#### 3.1.3 获取集合列表

获取所有已创建的集合信息。

```java
ServerResponse<List<VectoRexEntity>> response = client.getCollections();
```

### 3.2 数据管理

#### 3.2.1 添加数据

向指定集合中添加数据，数据以键值对形式提供。

```java
CollectionDataAddReq req = CollectionDataAddReq.builder()
    .collectionName("your-collection-name")
    .metadata(new HashMap<String, Object>() {{
        put("key1", "value1");
        put("key2", 123);
    }})
    .build();
ServerResponse<Void> response = client.addCollectionData(req);
```

#### 3.2.2 更新数据

更新指定集合中的数据，与添加数据类似，通过集合名称和数据唯一标识进行定位。

```java
CollectionDataAddReq req = CollectionDataAddReq.builder()
    .collectionName("your-collection-name")
    .metadata(new HashMap<String, Object>() {{
        put("key1", "updated-value1");
        put("key2", 456);
    }})
    .build();
ServerResponse<Void> response = client.updateCollectionData(req);
```

#### 3.2.3 删除数据

根据集合名称和数据唯一标识删除数据。

```java
CollectionDataDelReq req = new CollectionDataDelReq("your-collection-name", "data-id");
ServerResponse<Void> response = client.deleteCollectionData(req);
```

### 3.3 数据查询

#### 3.3.1 构建查询条件

使用 `QueryBuilder` 构建查询条件，支持多种条件组合。

```java
QueryBuilder builder = QueryBuilder.lambda("your-collection-name");
builder.eq("scalar-field-name", "value1")
    .gt("another-scalar-field-name", 100)
    .vector("vector-field-name", Arrays.asList(0.1f, 0.2f, 0.3f)) // 向量查询条件
    .topK(10); // 返回最相似的 10 条结果
```

#### 3.3.2 查询数据

执行查询操作，获取查询结果。

```java
ServerResponse<List<VectorSearchResult>> response = client.queryCollectionData(builder);
```

#### 3.3.3 分页查询数据

进行分页查询，获取指定页码的数据。

```java
builder.page(1, 10); // 查询第 1 页，每页 10 条数据
ServerResponse<PageResult<VectorSearchResult>> response = client.pageCollectionData(builder);
```

## 四、响应处理

### 4.1 成功响应

当请求成功时，`ServerResponse` 对象的 `status` 属性为 `ResponseCode.SUCCESS.getCode()`，可通过 `isSuccess()` 方法判断。

```java
if (response.isSuccess()) {
    // 处理成功逻辑，例如获取返回数据
    List<VectorSearchResult> data = response.getData();
}
```

### 4.2 错误响应

请求失败时，`status` 属性为其他值，可通过 `getMsg()` 方法获取错误信息。

```java
if (!response.isSuccess()) {
    // 处理错误逻辑
    String errorMessage = response.getMsg();
}
```

## 五、注意事项

1. **Token 管理**：SDK 内部会自动管理 Token 的获取和续期，Token 有效期为 2 小时，过期后会自动重新登录获取新 Token。
2. **异常处理**：在使用 SDK 时，应妥善处理可能抛出的 `IOException` 和 `RuntimeException`，确保应用的健壮性。
3. **线程安全**：`VectorRexClient` 实例是线程安全的，可在多线程环境中共享使用。

## 六、示例代码

### 6.1 完整示例

```java
public class VectorRexClientExample {
    public static void main(String[] args) {
        VectorRexClient client = new VectorRexClient("http://your-vectorrex-service-base-uri", "username", "password");

        // 创建集合
        VectoRexCollectionReq createReq = VectoRexCollectionReq.builder()
            .collectionName("example-collection")
            .vectorFileds(Arrays.asList(new VectorFiled("example-vector-field", 128)))
            .scalarFields(Arrays.asList(new ScalarField("example-scalar-field", ScalarFieldType.INT)))
            .build();
        ServerResponse<Void> createResponse = client.createCollection(createReq);
        if (createResponse.isSuccess()) {
            System.out.println("集合创建成功");
        } else {
            System.out.println("集合创建失败：" + createResponse.getMsg());
        }

        // 添加数据
        CollectionDataAddReq addReq = CollectionDataAddReq.builder()
            .collectionName("example-collection")
            .metadata(new HashMap<String, Object>() {{
                put("example-scalar-field", 123);
            }})
            .build();
        ServerResponse<Void> addResponse = client.addCollectionData(addReq);
        if (addResponse.isSuccess()) {
            System.out.println("数据添加成功");
        } else {
            System.out.println("数据添加失败：" + addResponse.getMsg());
        }

        // 查询数据
        QueryBuilder builder = QueryBuilder.lambda("example-collection");
        builder.eq("example-scalar-field", 123);
        ServerResponse<List<VectorSearchResult>> queryResponse = client.queryCollectionData(builder);
        if (queryResponse.isSuccess()) {
            List<VectorSearchResult> results = queryResponse.getData();
            for (VectorSearchResult result : results) {
                System.out.println("查询结果：" + result.getId() + ", 分数：" + result.getScore());
            }
        } else {
            System.out.println("数据查询失败：" + queryResponse.getMsg());
        }
    }
}
```

