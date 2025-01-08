package io.github.javpower.vectorexcore;

import io.github.javpower.vectorex.keynote.VectorDB;
import io.github.javpower.vectorex.keynote.model.VectorFiled;
import io.github.javpower.vectorex.keynote.storage.MapDBStorage;
import io.github.javpower.vectorexcore.entity.KeyValue;
import io.github.javpower.vectorexcore.entity.VectoRexEntity;
import org.mapdb.DB;
import org.mapdb.HTreeMap;

import java.util.List;
import java.util.stream.Collectors;

public class VectoRexClient {

    private final static Integer maxDataCount=1000000;//默认最大数据量
    private HTreeMap<String, VectoRexEntity> vectoRexEntityMap; //集合对应的数据结构
    private DB db;
    public VectoRexClient(String uri) {
        if(VectorDB.mapDBManager==null){
            if(uri==null){
                new VectorDB("vectorex/vectorex.db");
            }else {
                new VectorDB(uri);
            }
        }
        db=VectorDB.mapDBManager.getDb();
        vectoRexEntityMap=(HTreeMap<String, VectoRexEntity>)db.hashMap("VectoRexCollections").createOrOpen();
    }
    public void createCollection(VectoRexEntity milvusEntity) {
        List<VectorFiled> vectorFileds = milvusEntity.getVectorFileds().stream().map(KeyValue::getValue).collect(Collectors.toList());
        VectorDB.createCollection(milvusEntity.getCollectionName(),maxDataCount,vectorFileds);
        vectoRexEntityMap.put(milvusEntity.getCollectionName(),milvusEntity);
        db.commit();
    }
    public void delCollection(String collection) {
        VectorDB.delCollection(collection);
        vectoRexEntityMap.remove(collection);
        db.commit();
    }
    public MapDBStorage getStore(String collection) {
        MapDBStorage dataStore = VectorDB.getDataStore(collection);
        return dataStore;
    }
    public List<VectoRexEntity> getCollections() {
        return vectoRexEntityMap.values().stream().collect(Collectors.toList());
    }
    public VectoRexEntity getCollection(String collection) {
        return vectoRexEntityMap.get(collection);
    }


}
