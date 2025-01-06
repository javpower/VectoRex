package io.github.javpower.vectorexcore;

import io.github.javpower.vectorex.keynote.VectorDB;
import io.github.javpower.vectorex.keynote.model.VectorFiled;
import io.github.javpower.vectorex.keynote.storage.MapDBStorage;
import io.github.javpower.vectorexcore.entity.KeyValue;
import io.github.javpower.vectorexcore.entity.VectoRexEntity;

import java.util.List;
import java.util.stream.Collectors;

public class VectoRexClient {

    private final static Integer maxDataCount=1000000;//默认最大数据量
    private VectorDB vectorDB;
    public VectoRexClient(String uri) {
        if(uri==null){
            vectorDB= new VectorDB("vectorex/vectorex.db");
        }else {
            vectorDB=new VectorDB(uri);
        }
        vectorDB.init();
    }

    public void createCollection(VectoRexEntity milvusEntity) {
        List<VectorFiled> vectorFileds = milvusEntity.getVectorFileds().stream().map(KeyValue::getValue).collect(Collectors.toList());
        VectorDB.createCollection(milvusEntity.getCollectionName(),maxDataCount,vectorFileds);
    }
    public void delCollection(String collection) {
        VectorDB.delCollection(collection);
    }

    public MapDBStorage getStore(String collection) {
        MapDBStorage dataStore = VectorDB.getDataStore(collection);
        return dataStore;
    }

}
