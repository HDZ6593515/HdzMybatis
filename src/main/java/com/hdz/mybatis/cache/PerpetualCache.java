package com.hdz.mybatis.cache;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName PerpetualCache
 * @Description 一级缓存实现类
 * @Author 华达州
 * @Date 2021/8/28 19:08
 * @Version 1.0
 **/
public class PerpetualCache implements  Cache {


    /**
     * 缓存放在一个hashMap里面
     */
    private Map<Object,Object> cache = new HashMap<>();


    @Override
    public int getSize() {
        return cache.size();
    }

    @Override
    public Object getObject(Object key) {
        return cache.get(key);
    }

    @Override
    public Object putObject(Object key, Object value) {
        return cache.put(key,value);
    }

    @Override
    public Object removeObject(Object key) {
        return cache.remove(key);
    }

    @Override
    public void clearCache() {
        cache.clear();
    }
}
