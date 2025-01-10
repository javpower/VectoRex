package io.github.javpower.vectorextest.test;

import io.github.javpower.vectorexclient.VectorRexClient;
import io.github.javpower.vectorexclient.builder.QueryBuilder;
import io.github.javpower.vectorexclient.entity.MetricType;
import io.github.javpower.vectorexclient.entity.ScalarField;
import io.github.javpower.vectorexclient.entity.VectorFiled;
import io.github.javpower.vectorexclient.req.VectoRexCollectionReq;
import io.github.javpower.vectorexclient.res.ServerResponse;
import io.github.javpower.vectorexclient.res.VectorSearchResult;
import io.github.javpower.vectorexclient.util.GsonUtil;

import java.util.ArrayList;
import java.util.List;

public class TestRexClient {
    public static void main(String[] args) {
        VectorRexClient client = new VectorRexClient("https://vectorex.m78cloud.cn", "admin", "123456");
        System.out.println("========");
        List<ScalarField> scalarFields = new ArrayList();
        ScalarField id = ScalarField.builder().name("id").isPrimaryKey(true).build();
        ScalarField name = ScalarField.builder().name("name").isPrimaryKey(false).build();
        scalarFields.add(id);
        scalarFields.add(name);
        List<VectorFiled> vectorFileds = new ArrayList();
        VectorFiled vector = VectorFiled.builder().name("vector").metricType(MetricType.FLOAT_CANBERRA_DISTANCE).dimensions(3).build();
        vectorFileds.add(vector);
        ServerResponse<Void> face = client.createCollection(VectoRexCollectionReq.builder().collectionName("face").scalarFields(scalarFields).vectorFileds(vectorFileds).build());
        System.out.println(GsonUtil.toJson(face));
        System.out.println("=========");
        QueryBuilder eq = QueryBuilder.lambda("face").eq("id", 1);
        ServerResponse<List<VectorSearchResult>> collections = client.queryCollectionData(eq);
        System.out.println(GsonUtil.toJson(collections));
    }
}
