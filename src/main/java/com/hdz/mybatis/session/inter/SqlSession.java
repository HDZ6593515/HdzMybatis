package com.hdz.mybatis.session.inter;

import com.hdz.mybatis.config.Configuration;
import com.hdz.mybatis.executor.Executor;

import java.util.List;

/**
 * @InterfaceName SqlSession
 * @Description TODO
 * @Author 华达州
 * @Date 2021/8/19 11:55
 * @Version 1.0
 **/
public interface SqlSession {

    <T> T getMapper(Class<T> clazz);

    Configuration getConfiguration();

    <E> E selectOne(String statementId);

    <E> E selectOne(String statementId,Object parameter);


    <E> List<E> selectList(String statementId);

    <E> List<E> selectList(String statementId,Object parameter);

    int update(String statementId,Object parameter);

    int insert(String statementId,Object parameter);

    Executor getExecutor();


    int delete(String statementId, Object[] args);
}
