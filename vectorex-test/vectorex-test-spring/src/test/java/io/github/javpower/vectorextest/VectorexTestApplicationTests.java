package io.github.javpower.vectorextest;

import io.github.javpower.vectorexbootstater.core.VectoRexResult;
import io.github.javpower.vectorexcore.VectoRexClient;
import io.github.javpower.vectorextest.mapper.FaceMapper;
import io.github.javpower.vectorextest.model.Face;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SpringBootTest
class VectorexTestApplicationTests {

    @Autowired
    private FaceMapper faceMapper;
    @Autowired
    private VectoRexClient client;
    @Test
    void testFace() {
        List<VectoRexResult<Face>> query;
//         //新增
        List<Face> faces = generateFaces(20);
        for (int i = 0; i < faces.size(); i++) {
            System.out.println(i+1);
            faceMapper.insert(faces.get(i));
        }
        System.out.printf("======");
         //查询
//        query = faceMapper.queryWrapper().query();
//        System.out.println(GsonUtil.toJson(query));
        List<Float> floats = generateRandomVector(128);
        StringBuffer stringBuffer=new StringBuffer();
        for (int i = 0; i < 100; i++) {
            long startTime = System.currentTimeMillis(); // 记录开始时间
            query = faceMapper.queryWrapper().textVector(Face::getName, "face").topK(1).query();
            long endTime = System.currentTimeMillis(); // 记录结束时间
            long duration = endTime - startTime; // 计算执行时间
            stringBuffer.append(duration).append(",");
        }

        System.out.println(stringBuffer.toString());
//        System.out.println(GsonUtil.toJson(query));
//        //删除
//        faceMapper.removeById(1);
//        //查询
//        query = faceMapper.queryWrapper().eq(Face::getName, "Face 1").query();
//        System.out.println(GsonUtil.toJson(query));
//        List<VectoRexEntity> collections = client.getCollections();
//        System.out.printf(GsonUtil.toJson(collections));
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
