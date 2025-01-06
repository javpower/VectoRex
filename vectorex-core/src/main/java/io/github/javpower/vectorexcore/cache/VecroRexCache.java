package io.github.javpower.vectorexcore.cache;

import io.github.javpower.vectorexcore.entity.VectoRexEntity;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class VecroRexCache {

    public static final Map<String, VectoRexEntity> rexCache=new ConcurrentHashMap<>(); //类名-->缓存
    public static final Map<String, String> primaryKey=new ConcurrentHashMap<>();// 类名-->主键

}
