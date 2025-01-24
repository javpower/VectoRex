package io.github.javpower.vectorextest.test;

import io.github.javpower.vectorexclient.VectorRexClient;
import io.github.javpower.vectorexclient.builder.QueryBuilder;
import io.github.javpower.vectorexclient.entity.MetricType;
import io.github.javpower.vectorexclient.entity.ScalarField;
import io.github.javpower.vectorexclient.entity.VectorFiled;
import io.github.javpower.vectorexclient.req.CollectionDataAddReq;
import io.github.javpower.vectorexclient.req.VectoRexCollectionReq;
import io.github.javpower.vectorexclient.res.PageResult;
import io.github.javpower.vectorexclient.res.ServerResponse;
import io.github.javpower.vectorexclient.res.VectorSearchResult;
import io.github.javpower.vectorexclient.util.GsonUtil;
import io.github.javpower.vectorextest.model.Face;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TestRexClient {
    public static void main(String[] args) {
        VectorRexClient client = new VectorRexClient("https://vectorex.m78cloud.cn", "admin", "123456");
//        System.out.println("========创建集合========");
//        List<ScalarField> scalarFields = new ArrayList();
//        ScalarField id = ScalarField.builder().name("id").isPrimaryKey(true).build();
//        ScalarField name = ScalarField.builder().name("name").isPrimaryKey(false).build();
//        scalarFields.add(id);
//        scalarFields.add(name);
//        List<VectorFiled> vectorFields = new ArrayList();
//        VectorFiled vector = VectorFiled.builder().name("vector").metricType(MetricType.FLOAT_CANBERRA_DISTANCE).dimensions(128).build();
//        vectorFields.add(vector);
//        ServerResponse<Void> face = client.createCollection(VectoRexCollectionReq.builder().collectionName("face").scalarFields(scalarFields).vectorFileds(vectorFields).build());
//        System.out.println(GsonUtil.toJson(face));
//
//        System.out.println("=========添加数据========");
//        List<Face> faces = generateFaces(100000);
//        for (int i = 0; i < faces.size(); i++) {
//            System.out.println("add data"+(i+1));
//            client.addCollectionData(CollectionDataAddReq.builder().collectionName("face").metadata(GsonUtil.fromJsonToMap(GsonUtil.toJson(faces.get(i)))).build());
//        }

//        System.out.println("=========查询数据========");
        QueryBuilder eq = QueryBuilder.lambda("face").vector("vector", generateRandomVector(128)).topK(1).page(1,10);
//        ServerResponse<PageResult<VectorSearchResult>> collections = client.pageCollectionData(eq);
//        System.out.println(GsonUtil.toJson(collections));

        System.out.println("=========测试性能========");
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 100; i++) {
            long start = System.currentTimeMillis();
            client.queryCollectionData(eq);
            long end = System.currentTimeMillis();
            sb.append(end-start).append(",");
        }
        System.out.println(sb.toString());
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
