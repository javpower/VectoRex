package io.github.javpower.vectorexserver.service;

import io.github.javpower.vectorex.keynote.core.DbData;
import io.github.javpower.vectorex.keynote.core.VectorData;
import io.github.javpower.vectorex.keynote.core.VectorSearchResult;
import io.github.javpower.vectorex.keynote.model.VectorFiled;
import io.github.javpower.vectorex.keynote.query.ConditionBuilder;
import io.github.javpower.vectorex.keynote.storage.MapDBStorage;
import io.github.javpower.vectorexcore.VectoRexClient;
import io.github.javpower.vectorexcore.entity.KeyValue;
import io.github.javpower.vectorexcore.entity.ScalarField;
import io.github.javpower.vectorexcore.entity.VectoRexEntity;
import io.github.javpower.vectorexserver.exception.CommonException;
import io.github.javpower.vectorexserver.req.*;
import io.github.javpower.vectorexserver.response.PageResult;
import io.github.javpower.vectorexserver.response.ServerResponse;
import io.github.javpower.vectorexserver.util.PaginationUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class VectoRexService {
    private final VectoRexClient vectoRexClient;

    public VectoRexService(VectoRexClient vectoRexClient) {
        this.vectoRexClient = vectoRexClient;
    }

    //创建集合
    public void createCollection(VectoRexCollectionReq req) {
        long count = req.getScalarFields().stream().filter(v -> v.getIsPrimaryKey()).count();
        if (count > 1|| count == 0) {
            //报错
            throw CommonException.create(ServerResponse.createByError("主键不唯一"));
        }
        VectoRexEntity entity=new VectoRexEntity();
        entity.setCollectionName(req.getCollectionName());
        List<KeyValue<String, VectorFiled>> vectorFileds=new ArrayList<>();
        for (VectorFiled v : req.getVectorFileds()) {
            vectorFileds.add(new KeyValue(v.getName(),v));
        }
        List<KeyValue<String, ScalarField>> scalarFields=new ArrayList<>();
        for (ScalarField s : req.getScalarFields()) {
            scalarFields.add(new KeyValue(s.getName(),s));
        }
        entity.setVectorFileds(vectorFileds);
        entity.setScalarFields(scalarFields);
        vectoRexClient.createCollection(entity);
    }
    //删除集合
    public void delCollection(String collection) {
        vectoRexClient.delCollection(collection);
    }
    //获取集合
    public VectoRexEntity getCollection(String collection) {
        return vectoRexClient.getCollection(collection);
    }
    //获取集合列表
    public List<VectoRexEntity> getCollections() {
        return vectoRexClient.getCollections();
    }

    //添加集合数据
    public void add(CollectionDataAddReq req) {
        String collection=req.getCollectionName();
        Map<String, Object> metadata = req.getMetadata();
        VectoRexEntity structure = getCollection(collection);
        MapDBStorage store = vectoRexClient.getStore(collection);
        DbData data = new DbData();
        List<KeyValue<String, ScalarField>> scalarFields = structure.getScalarFields();
        List<KeyValue<String, VectorFiled>> vectorFields = structure.getVectorFileds();
        String id=null;
        for (KeyValue<String, ScalarField> scalarField : scalarFields) {
            String key = scalarField.getKey();
            if (scalarField.getValue().getIsPrimaryKey()) {
                id=metadata.get(key).toString();
            }
        }
        if(id==null){
            throw CommonException.create(ServerResponse.createByError("主键数据不能为空"));
        }
        List<VectorData> vectorFiledList=new ArrayList<>();
        for (KeyValue<String, VectorFiled> vectorField : vectorFields) {
            String key = vectorField.getKey();
            List<Double> valueArray = (List<Double>) metadata.get(key);
            VectorFiled vectorFiled = vectorField.getValue();
            if(vectorFiled.getDimensions()!=valueArray.size()) {
                throw CommonException.create(ServerResponse.createByError("向量数据维度不匹配"));
            }
            float[] vector = new float[valueArray.size()];
            for (int i = 0; i < valueArray.size(); i++) {
                vector[i] = valueArray.get(i).floatValue();
            }
            VectorData vd=new VectorData(id,vector);
            vd.setName(vectorFiled.getName());
            vectorFiledList.add(vd);
        }
        data.setId(id);
        data.setMetadata(metadata);
        data.setVectorFiled(vectorFiledList);
        store.save(data);
    }

    //删除集合数据
    public void delete(CollectionDataDelReq req) {
        MapDBStorage store = vectoRexClient.getStore(req.getCollectionName());
        store.delete(req.getId());
    }
    //分页查询
    public PageResult<VectorSearchResult> page(CollectionDataPageReq req) {
        CollectionDataQueryReq queryReq=new CollectionDataQueryReq();
        BeanUtils.copyProperties(req,queryReq);
        List<VectorSearchResult> query = query(queryReq);
        // 使用分页工具类进行分页
        return PaginationUtil.paginate(query, req.getPageIndex(), req.getPageSize());
    }

    //查询集合数据
    public List<VectorSearchResult> query(CollectionDataQueryReq req) {
        MapDBStorage store = vectoRexClient.getStore(req.getCollectionName());
        VectoRexEntity structure = getCollection(req.getCollectionName());
        ConditionBuilder builder=new ConditionBuilder();
        if(req.getQuery()!=null){
            List<ConditionFiledReq> query = req.getQuery();
            query.forEach(e->{
                getConditionBuilder(builder,e.getConditionFiledReq());
            });
        }
        if(req.getVector()!=null&&req.getVectorFieldName()!=null){
            Optional<KeyValue<String, VectorFiled>> first = structure.getVectorFileds().stream().filter(v -> Objects.equals(v.getKey(), req.getVectorFieldName())).findFirst();
            if(first.isPresent()){
                KeyValue<String, VectorFiled> vfv = first.get();
                if(vfv.getValue().getDimensions()!=req.getVector().size()) {
                    throw CommonException.create(ServerResponse.createByError("向量数据维度不匹配"));
                }
            }
        }
        if(StringUtils.isEmpty(req.getVectorFieldName())||req.getVector()==null){
            return store.query(builder);
        }else if(builder.getOp().size()==0){
            return store.search(req.getVectorFieldName(), req.getVector(), req.getTopK(), null);
        } else {
            return store.search(req.getVectorFieldName(), req.getVector(), req.getTopK(), builder);
        }
    }

    private ConditionBuilder getConditionBuilder(ConditionBuilder builder,List<ConditionFiledReq> ee) {
       ee.forEach(e->{
           String k = e.getField();
           Object value = e.getValue();
           String op = e.getOperator();
           switch (op){
               case "eq":
                   builder.eq(k,value);
                   break;
               case "in":
                   builder.in(k, (List<?>) value);
                   break;
               case "like":
                   builder.like(k,value.toString());
                   break;
               case "between":
                   builder.between(k, e.getStart(),e.getEnd());
                   break;
               case "gt":
                   builder.gt(k, (Comparable) value);
                   break;
               case "lt":
                   builder.lt(k, (Comparable) value);
                   break;
               case "ge":
                   builder.ge(k, (Comparable) value);
                   break;
               case "le":
                   builder.le(k, (Comparable) value);
                   break;
               case "and":
                   List<ConditionFiledReq> and = e.getConditionFiledReq();
                   ConditionBuilder and_other = new ConditionBuilder();
                   getConditionBuilder(and_other,and);
                   builder.and(and_other);
                   break;
               case "or":
                   List<ConditionFiledReq> or = e.getConditionFiledReq();
                   ConditionBuilder or_other =new ConditionBuilder();
                   getConditionBuilder(or_other,or);
                   builder.or(or_other);
                   break;
               default:
                   throw new IllegalArgumentException("Unsupported operator: " + op);
           }
       });

        return builder;
    }


}
