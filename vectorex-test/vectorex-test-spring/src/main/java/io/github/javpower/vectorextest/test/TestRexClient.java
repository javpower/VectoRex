package io.github.javpower.vectorextest.test;

import com.google.common.collect.Lists;
import io.github.javpower.vectorexclient.VectorRexClient;
import io.github.javpower.vectorexclient.builder.QueryBuilder;
import io.github.javpower.vectorexclient.res.PageResult;
import io.github.javpower.vectorexclient.res.ServerResponse;
import io.github.javpower.vectorexclient.res.VectorSearchResult;
import io.github.javpower.vectorexclient.util.GsonUtil;

import java.util.List;

public class TestRexClient {
    public static void main(String[] args) {
        VectorRexClient client = new VectorRexClient("https://vectorex.m78cloud.cn", "admin", "123456");
//        System.out.println("========");
//        List<ScalarField> scalarFields = new ArrayList();
//        ScalarField id = ScalarField.builder().name("id").isPrimaryKey(true).build();
//        ScalarField name = ScalarField.builder().name("name").isPrimaryKey(false).build();
//        scalarFields.add(id);
//        scalarFields.add(name);
//        List<VectorFiled> vectorFileds = new ArrayList();
//        VectorFiled vector = VectorFiled.builder().name("vector").metricType(MetricType.FLOAT_CANBERRA_DISTANCE).dimensions(3).build();
//        vectorFileds.add(vector);
//        ServerResponse<Void> face = client.createCollection(VectoRexCollectionReq.builder().collectionName("face").scalarFields(scalarFields).vectorFileds(vectorFileds).build());
//        System.out.println(GsonUtil.toJson(face));
//        System.out.println("=========");
        QueryBuilder eq = QueryBuilder.lambda("face").vector("vector", Lists.newArrayList(0.1f, 0.2f, 0.3f)).topK(1).page(1,10);
        ServerResponse<PageResult<VectorSearchResult>> collections = client.pageCollectionData(eq);
        PageResult<VectorSearchResult> page = collections.getData();
        List<VectorSearchResult> data = page.getData();
        VectorSearchResult vectorSearchResult = data.get(0);
        System.out.println(GsonUtil.toJson(collections));
    }
}
