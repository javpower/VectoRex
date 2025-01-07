package io.github.javpower.vectorexbootstater.mapper;

import com.google.common.collect.Lists;
import io.github.javpower.vectorex.keynote.core.DbData;
import io.github.javpower.vectorex.keynote.core.VectorData;
import io.github.javpower.vectorex.keynote.model.VectorFiled;
import io.github.javpower.vectorexbootstater.builder.LambdaQueryWrapper;
import io.github.javpower.vectorexbootstater.core.VectoRexResult;
import io.github.javpower.vectorexcore.VectoRexClient;
import io.github.javpower.vectorexcore.annotation.VectoRexCollection;
import io.github.javpower.vectorexcore.cache.VecroRexCache;
import io.github.javpower.vectorexcore.entity.KeyValue;
import io.github.javpower.vectorexcore.entity.ScalarField;
import io.github.javpower.vectorexcore.entity.VectoRexEntity;
import io.github.javpower.vectorexcore.util.GsonUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author xgc
 **/
@Slf4j
public abstract class BaseVectoRexMapper<T> {

    public abstract VectoRexClient getClient();

    public Class<T> getEntityType() {
        Type type = ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return (Class<T>) type;
    }

    private VectoRexCollection getCollectionAnnotation(Class<T> entityType) {
        VectoRexCollection collectionAnnotation = entityType.getAnnotation(VectoRexCollection.class);
        if (collectionAnnotation == null) {
            throw new IllegalStateException("Entity type " + entityType.getName() + " is not annotated with @VectoRexCollection.");
        }
        return collectionAnnotation;
    }

    private VectoRexEntity getVectoRexEntity(Class<T> entityType) {
        return VecroRexCache.rexCache.get(entityType.getName());
    }

    private String getPrimaryKey(Class<T> entityType) {
        return VecroRexCache.primaryKey.get(entityType.getName());
    }

    private Map<String, String> getToFieldMap(Class<T> entityType) {
        return VecroRexCache.toField.get(entityType.getName());
    }

    public LambdaQueryWrapper<T> queryWrapper() {
        Class<T> entityType = getEntityType();
        VectoRexCollection collectionAnnotation = getCollectionAnnotation(entityType);
        return new LambdaQueryWrapper<>(getClient(), collectionAnnotation.name(), entityType);
    }


    public VectoRexResult getById(Serializable id) {
        Class<T> entityType = getEntityType();
        VectoRexCollection collectionAnnotation = getCollectionAnnotation(entityType);
        DbData data = getClient().getStore(collectionAnnotation.name()).get(id.toString());
        VectoRexResult result = new VectoRexResult<>();
        Map<String, Object> metadata = data.getMetadata();
        Map<String, Object> properties = new HashMap<>();
        Map<String, String> toField = getToFieldMap(entityType);
        for (Map.Entry<String, Object> entry : metadata.entrySet()) {
            if (toField.containsKey(entry.getKey())) {
                properties.put(toField.get(entry.getKey()), entry.getValue());
            }
        }
        T entity = GsonUtil.convertMapToType(properties, entityType);
        result.setScore(0.0f);
        result.setEntity(entity);
        return result;
    }

    public void removeById(Serializable id) {
        Class<T> entityType = getEntityType();
        VectoRexCollection collectionAnnotation = getCollectionAnnotation(entityType);
        getClient().getStore(collectionAnnotation.name()).delete(id.toString());
    }

    private List<DbData> convertEntitiesToDbData(Collection<T> entities, Class<T> entityType) {
        VectoRexEntity vectoRex = getVectoRexEntity(entityType);
        String primaryKey = getPrimaryKey(entityType);
        List<KeyValue<String, VectorFiled>> vectorFields = vectoRex.getVectorFileds();
        List<KeyValue<String, ScalarField>> scalarFields = vectoRex.getScalarFields();
        List<DbData> dbDatas = new ArrayList<>();

        for (T entity : entities) {
            DbData dbData = new DbData();
            Map<String, Object> data = GsonUtil.fromJsonToMap(GsonUtil.toJson(entity));
            String id = convertToIntegerString(data.get(primaryKey));
            List<VectorData> vectorData = new ArrayList<>();
            Map<String, Object> metadata = new HashMap<>();

            for (KeyValue<String, VectorFiled> vectorField : vectorFields) {
                Object valueObject = data.get(vectorField.getKey());
                metadata.put(vectorField.getValue().getName(), valueObject);
                if (valueObject instanceof List<?>) {
                    List<?> valueList = (List<?>) valueObject;
                    if (!valueList.isEmpty()) {
                        float[] valueArray = new float[valueList.size()];
                        for (int i = 0; i < valueList.size(); i++) {
                            Object element = valueList.get(i);
                            if (element instanceof Number) {
                                valueArray[i] = ((Number) element).floatValue();
                            } else {
                                throw new IllegalArgumentException("List element is not a Number: " + element);
                            }
                        }
                        VectorData vectorData1 = new VectorData(id, valueArray);
                        vectorData1.setName(vectorField.getValue().getName());
                        vectorData.add(vectorData1);
                    }
                }
            }

            for (KeyValue<String, ScalarField> scalarField : scalarFields) {
                Object valueObject = data.get(scalarField.getKey());
                metadata.put(scalarField.getValue().getName(), valueObject);
            }

            dbData.setId(id);
            dbData.setVectorFiled(vectorData);
            dbData.setMetadata(metadata);
            dbDatas.add(dbData);
        }

        return dbDatas;
    }

    public void insert(Collection<T> entities) {
        Class<T> entityType = getEntityType();
        VectoRexCollection collectionAnnotation = getCollectionAnnotation(entityType);
        List<DbData> dbDatas = convertEntitiesToDbData(entities, entityType);
        getClient().getStore(collectionAnnotation.name()).saveAll(dbDatas);
    }
    public void insert(T entities) {
        insert(Lists.newArrayList(entities));
    }
    public void updateById(T entities) {
       updateById(Lists.newArrayList(entities));
    }

    public void updateById(Collection<T> entities) {
        Class<T> entityType = getEntityType();
        VectoRexCollection collectionAnnotation = getCollectionAnnotation(entityType);
        List<DbData> dbDatas = convertEntitiesToDbData(entities, entityType);
        for (DbData dbData : dbDatas) {
            getClient().getStore(collectionAnnotation.name()).update(dbData);
        }
    }
    private static String convertToIntegerString(Object value) {
        if (value instanceof Number) {
            BigDecimal bd = BigDecimal.valueOf(((Number) value).doubleValue());
            return bd.toBigInteger().toString();
        } else {
            return value.toString();
        }
    }
}