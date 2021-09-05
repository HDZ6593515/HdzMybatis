package com.hdz.mybatis.mapping;

import com.hdz.mybatis.config.Configuration;
import com.hdz.mybatis.session.inter.SqlSession;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName MapperRegistry
 * @Description TODO
 * @Author 华达州
 * @Date 2021/8/19 16:46
 * @Version 1.0
 **/
public class MapperRegistry {

    private final Map<Class<?>,MapperProxyFactory<?>> knowMappers  = new HashMap<>();
    
    private final Configuration configuration;
    
    public MapperRegistry(Configuration configuration){
        this.configuration = configuration;
    }

    public <T> void addMapper(Class<T> type){
        if(type.isInterface()){
            if(this.configuration.hasMapper(type)){
                return;
            }
            this.knowMappers.put(type,new MapperProxyFactory<>(type));
            MapperAnnotationBuilder parse = new MapperAnnotationBuilder(this.configuration, type);
            parse.parse();
        }
        
    }

    public <T> T getMapper(Class<T> type, SqlSession sqlSession){
        MapperProxyFactory<T> mapperProxyFactory = (MapperProxyFactory<T>)this.knowMappers.get(type);
        return mapperProxyFactory.newInstance(sqlSession);
    }

    public <T> boolean hasMapper(Class<T> type) {
        return this.knowMappers.containsKey(type);
    }


}
