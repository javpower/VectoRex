# vectorex-starter

## 一、概述

`vectorex-starter` 是一个用于简化 VectoRex 服务集成的 Spring Boot Starter。它提供了自动配置和便捷的 API，帮助开发者快速在 Spring Boot 应用中使用 VectoRex 功能。

## 二、安装

### 2.1 依赖添加

在项目的 `pom.xml` 文件中添加以下依赖：

```xml
<dependency>
    <groupId>io.github.javpower</groupId>
    <artifactId>vectorex-starter</artifactId>
    <version>1.0.0</version>
</dependency>
```

## 三、配置

### 3.1 应用配置

在 `application.yml` 或 `application.properties` 文件中添加 VectoRex 配置：

```yaml
vectorex:
  enable: true
  packages:
    - io.github.javpower.vectorextest.model
```

### 3.2 配置说明

- `vectorex.enable`：是否启用 VectoRex 功能，默认为 `true`。
- `vectorex.packages`：指定包含 VectoRex 实体类的包路径，用于自动扫描和注册。

## 四、基本用法

### 4.1 实体类定义

定义一个继承自 `VectoRexEntity` 的实体类，用于表示存储在 VectoRex 中的数据。

```java
import io.github.javpower.vectorex.entity.VectoRexEntity;
import lombok.Data;

@Data
public class Face extends VectoRexEntity {
    private Long id;
    private String name;
    private List<Float> vector;
}
```

### 4.2 Mapper 接口定义

定义一个继承自 `VectoRexMapper` 的 Mapper 接口，用于操作 VectoRex 数据。

```java
import io.github.javpower.vectorex.mapper.VectoRexMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FaceMapper extends VectoRexMapper<Face> {
}
```

### 4.3 使用示例

在测试类中使用 `FaceMapper` 进行数据操作。

```java
import io.github.javpower.vectorex.mapper.VectoRexResult;
import io.github.javpower.vectorex.query.VectoRexQueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class VectorexTestApplicationTests {

    @Autowired
    private FaceMapper faceMapper;

    @Test
    void testFace() {
        List<VectoRexResult<Face>> query;

        // 新增数据
        List<Face> faces = generateFaces(5);
        faceMapper.insert(faces);

        // 查询数据
        query = faceMapper.queryWrapper().eq(Face::getName, "Face 1").query();
        System.out.println(GsonUtil.toJson(query));

        List<Float> floats = generateRandomVector(128);
        query = faceMapper.queryWrapper().vector(Face::getVector, floats).query();
        System.out.println(GsonUtil.toJson(query));

        // 删除数据
        faceMapper.removeById(1);

        // 查询数据
        query = faceMapper.queryWrapper().eq(Face::getName, "Face 1").query();
        System.out.println(GsonUtil.toJson(query));
    }

    public static List<Face> generateFaces(int count) {
        List<Face> faces = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Face face = new Face();
            face.setId((long) (i + 1)); // 生成从1开始的ID
            face.setName("Face " + (i + 1)); // 生成简单的名称
            face.setVector(generateRandomVector(128)); // 生成128维的随机向量
            faces.add(face);
        }
        return faces;
    }

    private static List<Float> generateRandomVector(int dimension) {
        List<Float> vector = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < dimension; i++) {
            vector.add(random.nextFloat()); // 生成0到1之间的随机浮点数
        }
        return vector;
    }
}
```