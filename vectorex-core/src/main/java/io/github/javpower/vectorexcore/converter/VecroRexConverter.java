package io.github.javpower.vectorexcore.converter;


import io.github.javpower.vectorex.keynote.model.VectorFiled;
import io.github.javpower.vectorexcore.annotation.VectoRexCollection;
import io.github.javpower.vectorexcore.annotation.VectoRexField;
import io.github.javpower.vectorexcore.cache.VecroRexCache;
import io.github.javpower.vectorexcore.entity.DataType;
import io.github.javpower.vectorexcore.entity.KeyValue;
import io.github.javpower.vectorexcore.entity.ScalarField;
import io.github.javpower.vectorexcore.entity.VectoRexEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author xgc
 **/
@Slf4j
public class VecroRexConverter {


    public static VectoRexEntity convert(Class<?> entityClass) {
        VectoRexEntity cache = VecroRexCache.rexCache.get(entityClass.getName());
        if (Objects.nonNull(cache)) {
            return cache;
        }
        VectoRexEntity vectoRex = new VectoRexEntity();
        List<KeyValue<String, VectorFiled>> vectorFileds = new ArrayList<>();
        List<KeyValue<String, ScalarField>> scalarFields = new ArrayList<>();

        // 集合名称
        VectoRexCollection collectionAnnotation = entityClass.getAnnotation(VectoRexCollection.class);
        if (Objects.isNull(collectionAnnotation)) {
            throw new IllegalArgumentException("Entity must be annotated with @VectoRexCollection");
        }

        // 集合名称
        String collectionName = collectionAnnotation.name();
        vectoRex.setCollectionName(collectionName);

        List<Field> fields = getAllFieldsFromClass(entityClass);
        // 遍历实体类的所有字段，读取@MilvusField注解信息
        Map<String,String> toField = new HashMap<>();
        for (Field field : fields) {

            VectoRexField fieldAnnotation = field.getAnnotation(VectoRexField.class);
            if (Objects.isNull(fieldAnnotation)) {
                continue;
            }
            // 处理字段名，优先使用注解中的字段名，若无则用反射获取的字段名
            String fieldName = fieldAnnotation.name().isEmpty() ? field.getName() : fieldAnnotation.name();
            toField.put(fieldName,field.getName());
            // 缓存属性名与函数名的映射
            if (fieldAnnotation.dataType().equals(DataType.Scalar)) {
                ScalarField scalarField = new ScalarField();
                scalarField.setName(fieldName);
                scalarField.setIsPrimaryKey(fieldAnnotation.isPrimaryKey());
                KeyValue<String, ScalarField> keyValue = new KeyValue<>(field.getName(), scalarField);
                scalarFields.add(keyValue);
                if (fieldAnnotation.isPrimaryKey()) {
                    VecroRexCache.primaryKey.put(entityClass.getName(), field.getName());
                }
            } else {
                boolean listFloat = isListFloat(field);
                if(!listFloat){
                    throw new IllegalArgumentException("vector filed type err");
                }
                VectorFiled vectorFiled = new VectorFiled();
                vectorFiled.setName(fieldName);
                vectorFiled.setDimensions(fieldAnnotation.dimension());
                vectorFiled.setMetricType(fieldAnnotation.metricType());
                KeyValue<String, VectorFiled> keyValue = new KeyValue<>(field.getName(),vectorFiled);
                vectorFileds.add(keyValue);
            }
        }
        vectoRex.setVectorFileds(vectorFileds);
        vectoRex.setScalarFields(scalarFields);
        if(CollectionUtils.isEmpty(vectorFileds)){
            throw new IllegalArgumentException("the vector filed does not null");
        }
        if(VecroRexCache.primaryKey.get(entityClass.getName())==null){
            throw new IllegalArgumentException("the primary key does not null");
        }
        VecroRexCache.rexCache.put(entityClass.getName(),vectoRex);
        VecroRexCache.toField.put(entityClass.getName(),toField);
        return vectoRex;
    }
    /**
     * 递归获取类及其所有父类的所有字段。
     *
     * @param clazz 要检查的类。
     * @return 包含所有字段的列表。
     */
    public static List<Field> getAllFieldsFromClass(Class<?> clazz) {
        List<Field> fields = new ArrayList<>();
        // 递归地获取字段直到Object类
        while (clazz != null && clazz != Object.class) {
            // 获取当前类的所有字段并添加到列表中
            fields.addAll(Stream.of(clazz.getDeclaredFields())
                    .peek(field -> field.setAccessible(true)) // 确保可以访问私有字段
                    .collect(Collectors.toList()));
            clazz = clazz.getSuperclass(); // 移动到父类
        }
        return fields;
    }

    /**
     * 判断字段是否是 List<Float> 类型。
     *
     * @param field 要检查的字段
     * @return 如果字段是 List<Float> 类型返回 true，否则返回 false
     */
    public static boolean isListFloat(Field field) {
        // 确保字段不是 null
        if (field == null) {
            return false;
        }
        // 获取字段的泛型类型
        Type genericType = field.getGenericType();

        // 检查是否是参数化类型
        if (!(genericType instanceof ParameterizedType)) {
            return false;
        }
        // 类型转换
        ParameterizedType parameterizedType = (ParameterizedType) genericType;
        // 获取参数化类型的原始类型
        Type rawType = parameterizedType.getRawType();

        // 检查原始类型是否是 List.class
        if (!(rawType instanceof Class) || !List.class.isAssignableFrom((Class<?>) rawType)) {
            return false;
        }
        // 获取类型参数
        Type[] typeArguments = parameterizedType.getActualTypeArguments();
        // 检查类型参数是否正好有一个，并且是 Float.TYPE
        return typeArguments.length == 1 && typeArguments[0] == Float.class;
    }
}
