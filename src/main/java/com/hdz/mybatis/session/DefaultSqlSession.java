package com.hdz.mybatis.session;

import com.hdz.mybatis.config.Configuration;
import com.hdz.mybatis.exception.TooManyResultException;
import com.hdz.mybatis.executor.SimpleExecutor;
import com.hdz.mybatis.executor.Executor;
import com.hdz.mybatis.mapping.MappedStatement;
import com.hdz.mybatis.session.inter.SqlSession;

import java.util.List;

/**
 * @ClassName DefaultSqlSession
 * @Description TODO
 * @Author 华达州
 * @Date 2021/8/19 11:54
 * @Version 1.0
 **/
public class DefaultSqlSession implements SqlSession {

    private final Configuration configuration;


    private final Executor executor;

    public DefaultSqlSession(Configuration configuration){
        this.configuration = configuration;
        this.executor = this.configuration.newExecutor();
    }


    @Override
    public <T> T getMapper(Class<T> clazz) {
        return this.configuration.getMapper(clazz,this);
    }

    @Override
    public Configuration getConfiguration() {
        return this.configuration;
    }

    /**
     * 查询单条记录
     * @author 华达州
     * @date 2021/8/31 17:50
     * @param statementId   ID为全限定类目+方法名
     * @return E
     */
    @Override
    public <E> E selectOne(String statementId) {
        return selectOne(statementId,null);
    }

    @Override
    public <E> E selectOne(String statementId, Object parameter) {
        List<E> list = this.selectList(statementId, parameter);
        if(list.size()==1){
            return list.get(0);
        }
        else if(list.size()>1){
            throw new TooManyResultException("结果期望是1个，但是有"+list.size()+"个");
        }
        else {
            return null;
        }
    }

    @Override
    public <E> List<E> selectList(String statementId) {
        return selectList(statementId,null);
    }

    @Override
    public <E> List<E> selectList(String statementId, Object parameter) {
        MappedStatement ms = this.configuration.getMappedStatement(statementId);
        return this.executor.query(ms,parameter);
    }

    @Override
    public int update(String statementId, Object parameter) {
        MappedStatement ms = this.configuration.getMappedStatement(statementId);
        return this.executor.update(ms,parameter);
    }

    @Override
    public int insert(String statementId, Object parameter) {
        return update(statementId,parameter);
    }

    @Override
    public Executor getExecutor() {
        return null;
    }

    @Override
    public int delete(String statementId, Object[] args) {
        return 0;
    }
}
