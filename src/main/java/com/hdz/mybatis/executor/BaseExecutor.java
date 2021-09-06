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
        //先从缓存中获取
        List<E> list = (List<E>)localCache.getObject(cacheKey);
        
        //判断是否存在缓存，没有则查询数据库
        if(list==null){
            return queryFromDatabase(ms,parameter,cacheKey);
        }
        return list;
    }

    @Override
    public  <E> List<E> queryFromDatabase(MappedStatement ms, Object parameter, CacheKey cacheKey){
        
        List<E> list = doQuery(ms, parameter);
        //把查询的结果放入缓存中
        localCache.putObject(cacheKey,list);
        return list;
    }

    @Override
    public int update(MappedStatement ms, Object parameter) {
        //执行修改操作之前需要清空缓存
        localCache.clearCache();
        return doUpdate(ms,parameter);
    }

    /**
     * 创建缓存键
     *
     * @param ms        
     * @param parameter 
     * @return {@link CacheKey}
     */
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
