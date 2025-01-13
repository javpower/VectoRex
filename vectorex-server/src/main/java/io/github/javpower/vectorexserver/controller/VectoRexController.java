package io.github.javpower.vectorexserver.controller;

import io.github.javpower.vectorex.keynote.core.VectorSearchResult;
import io.github.javpower.vectorexcore.entity.VectoRexEntity;
import io.github.javpower.vectorexserver.req.*;
import io.github.javpower.vectorexserver.response.PageResult;
import io.github.javpower.vectorexserver.response.ServerResponse;
import io.github.javpower.vectorexserver.service.VectoRexService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vectorex")
@Slf4j
@Tag(name = "向量集合管理", description = "用于管理向量集合及其数据的接口")
public class VectoRexController {

    private final VectoRexService vectoRexService;

    public VectoRexController(VectoRexService vectoRexService) {
        this.vectoRexService = vectoRexService;
    }

    @PostMapping("/add/collections")
    @Operation(summary = "创建集合", description = "根据请求参数创建一个新的向量集合")
    public ServerResponse createCollection(@RequestBody VectoRexCollectionReq req) {
        log.info("createCollection request: {}", req);
        vectoRexService.createCollection(req);
        return ServerResponse.createBySuccess("集合创建成功");
    }

    @PostMapping("/del/collections/{collection}")
    @Operation(summary = "删除集合", description = "根据集合名称删除指定的向量集合")
    public ServerResponse delCollection(@PathVariable String collection) {
        log.info("delCollection request: collection={}", collection);
        vectoRexService.delCollection(collection);
        return ServerResponse.createBySuccess("集合删除成功");
    }

    @PostMapping("/get/collections")
    @Operation(summary = "获取集合列表", description = "获取所有向量集合的列表")
    public ServerResponse<List<VectoRexEntity>> getCollections() {
        log.info("getCollections request");
        return ServerResponse.createBySuccess("操作成功",vectoRexService.getCollections());
    }
    @PostMapping("/page/data/page")
    @Operation(summary = "分页查询集合列表", description = "根据条件查询向量集合的列表")
    public ServerResponse<PageResult<VectoRexEntity>> pageCollection(@RequestBody VectoRexCollectionPageReq req) {
        log.info("page request: {}", req);
        return ServerResponse.createBySuccess("操作成功",vectoRexService.pageCollection(req));
    }
    @PostMapping("/collections/data/add")
    @Operation(summary = "添加集合数据", description = "向指定集合中添加数据")
    public ServerResponse add(@RequestBody CollectionDataAddReq req) {
        log.info("add request: {}", req);
        vectoRexService.add(req);
        return ServerResponse.createBySuccess("数据添加成功");
    }

    @PostMapping("/collections/data/update")
    @Operation(summary = "更新集合数据", description = "更新指定集合中的数据")
    public ServerResponse update(@RequestBody CollectionDataAddReq req) {
        log.info("update request: {}", req);
        vectoRexService.add(req);
        return ServerResponse.createBySuccess("数据更新成功");
    }

    @PostMapping("/collections/data/del")
    @Operation(summary = "删除集合数据", description = "从指定集合中删除数据")
    public ServerResponse delete(@RequestBody CollectionDataDelReq req) {
        log.info("delete request: {}", req);
        vectoRexService.delete(req);
        return ServerResponse.createBySuccess("数据删除成功");
    }

    @PostMapping("/collections/data/query")
    @Operation(summary = "查询集合数据", description = "根据条件查询指定集合中的数据")
    public ServerResponse<List<VectorSearchResult>> query(@RequestBody CollectionDataQueryReq req) {
        log.info("query request: {}", req);
        return ServerResponse.createBySuccess("操作成功",vectoRexService.query(req));
    }
    @PostMapping("/collections/data/page")
    @Operation(summary = "分页查询集合数据", description = "根据条件查询指定集合中的数据")
    public ServerResponse<PageResult<VectorSearchResult>> page(@RequestBody CollectionDataPageReq req) {
        log.info("page request: {}", req);
        return ServerResponse.createBySuccess("操作成功",vectoRexService.page(req));
    }


}