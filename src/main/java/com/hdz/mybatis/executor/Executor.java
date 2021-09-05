package com.hdz.mybatis.executor;

import com.hdz.mybatis.cache.CacheKey;
import com.hdz.mybatis.mapping.MappedStatement;

import java.util.List;

/**
 * @InterfaceName Executor
 * @Description TODO
 * @Author 华达州
 * @Date 2021/8/19 12:26
 * @Version 1.0
 **/
public interface Executor {

    <E> List<E> query(MappedStatement ms, Object parameter);

    <E> List<E> query(MappedStatement ms, Object parameter, CacheKey cacheKey);

    
    int update(MappedStatement ms, Object parameter);
    
    CacheKey createCacheKey(MappedStatement ms,Object parameter);

    <E> List<E> queryFromDatabase(MappedStatement ms, Object parameter, CacheKey cacheKey);
    
    
}
