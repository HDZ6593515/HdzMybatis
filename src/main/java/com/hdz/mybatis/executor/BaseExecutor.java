package com.hdz.mybatis.executor;

import com.hdz.mybatis.cache.CacheKey;
import com.hdz.mybatis.cache.PerpetualCache;
import com.hdz.mybatis.config.Configuration;
import com.hdz.mybatis.mapping.MappedStatement;

import java.util.List;

/**
 * @ClassName BaseExecutor
 * @Description TODO
 * @Author 华达州
 * @Date 2021/8/28 17:57
 * @Version 1.0
 **/
public abstract class BaseExecutor implements Executor{
    
    protected Configuration configuration;
    
    protected PerpetualCache localCache;
    
    public BaseExecutor(Configuration configuration){
        this.configuration = configuration;
        this.localCache = new PerpetualCache();
    }

    @Override
    public <E> List<E> query(MappedStatement ms, Object parameter) {
        CacheKey cacheKey = createCacheKey(ms, parameter);
        return query(ms, parameter, cacheKey);
    }


    @Override
    public <E> List<E> query(MappedStatement ms, Object parameter, CacheKey cacheKey) {
        List<E> list = (List<E>)localCache.getObject(cacheKey);
        if(list==null){
            return queryFromDatabase(ms,parameter,cacheKey);
        }
        return list;
    }

    @Override
    public  <E> List<E> queryFromDatabase(MappedStatement ms, Object parameter, CacheKey cacheKey){
        List<E> list = doQuery(ms, parameter);
        localCache.putObject(cacheKey,list);
        return list;
    }

    @Override
    public int update(MappedStatement ms, Object parameter) {
        localCache.clearCache();
        return doUpdate(ms,parameter);
    }

    @Override
    public CacheKey createCacheKey(MappedStatement ms, Object parameter) {
        CacheKey cacheKey = new CacheKey();
        cacheKey.setSql(ms.getSql());
        cacheKey.setStatementId(ms.getSqlId());
        cacheKey.setParameter((Object[]) parameter);
        
        return cacheKey;
    }

    public abstract <E> List<E> doQuery(MappedStatement ms, Object parameter);
    
    public abstract int doUpdate(MappedStatement ms,Object parameter);
    
}
