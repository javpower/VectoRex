package io.github.javpower.vectorexclient;

import io.github.javpower.vectorexclient.builder.QueryBuilder;
import io.github.javpower.vectorexclient.entity.VectoRexEntity;
import io.github.javpower.vectorexclient.req.*;
import io.github.javpower.vectorexclient.res.PageResult;
import io.github.javpower.vectorexclient.res.ServerResponse;
import io.github.javpower.vectorexclient.res.VectorSearchResult;
import io.github.javpower.vectorexclient.util.GsonUtil;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.BeanUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class VectorRexClient {

    private static final long TOKEN_EXPIRE_TIME = 2 * 60 * 60 * 1000-2*60*1000; // Token expires in 2 hours

    private final String baseUri;
    private final String username;
    private final String password;
    private final OkHttpClient okHttpClient;
    private String token;
    private long tokenExpireTime;

    public VectorRexClient(String baseUri, String username, String password) {
        this.baseUri = baseUri;
        this.username = username;
        this.password = password;
        this.okHttpClient = new OkHttpClient();
        try {
            login();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ServerResponse<Void> createCollection(VectoRexCollectionReq req)  {
        return executeRequest("/vectorex/add/collections", req);
    }

    public ServerResponse<Void> delCollection(String collection)  {
        return executeRequest("/vectorex/del/collections/" + collection);
    }

    public ServerResponse<List<VectoRexEntity>> getCollections()  {
        ServerResponse<List<Map>> response = executeRequest("/vectorex/get/collections");
        if (response.isSuccess()) {
            List<Map> data = response.getData();
            List<VectoRexEntity> vectoRexEntities=new ArrayList<>();
            for (Map datum : data) {
                VectoRexEntity o = (VectoRexEntity)GsonUtil.convertMapToType(datum, VectoRexEntity.class);
                vectoRexEntities.add(o);
            }
            ServerResponse serverResponse = ServerResponse.createBySuccess(vectoRexEntities);
            return serverResponse;
        }else {
            return ServerResponse.createByError(response.getMsg());
        }
    }

    public ServerResponse<Void> addCollectionData(CollectionDataAddReq req)  {
        return executeRequest("/vectorex/collections/data/add", req);
    }

    public ServerResponse<Void> updateCollectionData(CollectionDataAddReq req)  {
        return executeRequest("/vectorex/collections/data/update", req);
    }

    public ServerResponse<Void> deleteCollectionData(CollectionDataDelReq req)  {
        return executeRequest("/vectorex/collections/data/del", req);
    }

    public ServerResponse<List<VectorSearchResult>> queryCollectionData(QueryBuilder queryBuilder)  {
        CollectionDataPageReq queryReq = queryBuilder.build();
        CollectionDataQueryReq req = new CollectionDataQueryReq();
        BeanUtils.copyProperties(queryReq,req);
        ServerResponse<List<Map>> response = executeRequest("/vectorex/collections/data/query", req);
        if (response.isSuccess()) {
            List<VectorSearchResult> vectorSearchResults=new ArrayList<>();
            List<Map> data = response.getData();
            for (Map datum : data) {
                VectorSearchResult o = (VectorSearchResult)GsonUtil.convertMapToType(datum, VectorSearchResult.class);
                vectorSearchResults.add(o);
            }
            ServerResponse serverResponse = ServerResponse.createBySuccess(vectorSearchResults);
            return serverResponse;
        }else {
            return ServerResponse.createByError(response.getMsg());
        }
    }

    public ServerResponse<PageResult<VectorSearchResult>> pageCollectionData(QueryBuilder queryBuilder)  {
        ServerResponse<Object> response = executeRequest("/vectorex/collections/data/page", queryBuilder.build());
        if (response.isSuccess()) {
            Object data = response.getData();
            String s = GsonUtil.toJson(data);
            PageResult<Map> pageResult = GsonUtil.fromJson(s, PageResult.class);
            List<VectorSearchResult> vectorSearchResults=new ArrayList<>();
            List<Map> resultData = pageResult.getData();
            for (Map datum : resultData) {
                VectorSearchResult o = (VectorSearchResult)GsonUtil.convertMapToType(datum, VectorSearchResult.class);
                vectorSearchResults.add(o);
            }
            PageResult<VectorSearchResult> resultPageResult = new PageResult<>(vectorSearchResults, pageResult.getTotalRecords(), pageResult.getPageIndex(), pageResult.getPageSize());
            ServerResponse serverResponse = ServerResponse.createBySuccess(resultPageResult);
            return serverResponse;
        }else {
            return ServerResponse.createByError(response.getMsg());
        }
    }

    private <T> ServerResponse<T> executeRequest(String path) {
        return executeRequest(path, new HashMap<>());
    }

    private <T> ServerResponse<T> executeRequest(String path, Object reqBody)  {
        log.debug("executeRequest path: {}, reqBody: {}", path, GsonUtil.toJson(reqBody));
        try {
            checkToken();
            String url = baseUri + path;
            RequestBody requestBody = reqBody != null ? RequestBody.create(GsonUtil.toJson(reqBody), MediaType.parse("application/json; charset=utf-8")) : null;
            Request request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .header("token", token)
                    .build();

            try (Response response = okHttpClient.newCall(request).execute()) {
                String responseBody = response.body().string();
                ServerResponse serverResponse = GsonUtil.fromJson(responseBody, ServerResponse.class);
                return serverResponse;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void checkToken() throws IOException {
        if (System.currentTimeMillis() >= tokenExpireTime) {
            login();
        }
    }

    private void login() throws IOException {
        String url = baseUri + "/vectorex/login";
        LoginUser loginUser = new LoginUser(username, password);
        RequestBody requestBody = RequestBody.create(GsonUtil.toJson(loginUser), MediaType.parse("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        try (Response response = okHttpClient.newCall(request).execute()) {
            String responseBody = response.body().string();
            ServerResponse serverResponse = GsonUtil.fromJson(responseBody, ServerResponse.class);
            if (serverResponse.isSuccess()) {
                token = (String) serverResponse.getData();
                tokenExpireTime = System.currentTimeMillis() + TOKEN_EXPIRE_TIME;
            } else {
                throw new RuntimeException("登录失败：" + serverResponse.getMsg());
            }
        }
    }

    public void close() {

    }
}