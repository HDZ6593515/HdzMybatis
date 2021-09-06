package com.hdz.mybatis.mapping;

import com.hdz.mybatis.session.inter.SqlSession;

import java.lang.reflect.Proxy;

/**
 * @ClassName MapperProxyFactory
 * @Description 生成Mapper代理对象的工厂类
 * @Author 华达州
 * @Date 2021/8/19 16:47
 * @Version 1.0
 **/
public class MapperProxyFactory<T> {

    private final Class<T> mapperInterface;

    public MapperProxyFactory(Class<T> mapperInterface){
        this.mapperInterface = mapperInterface;
    }


    public T newInstance(SqlSession sqlSession) {
        MapperProxy<T> mapperProxy = new MapperProxy<T>(sqlSession,this.mapperInterface);
        return this.newInstance(mapperProxy);
    }

    public <T> T newInstance(MapperProxy<T> mapperProxy){
        return (T) Proxy.newProxyInstance(this.mapperInterface.getClassLoader(),new Class[]{this.mapperInterface},mapperProxy);
    }

}
