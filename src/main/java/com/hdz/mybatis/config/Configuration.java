package com.hdz.mybatis.config;

import com.hdz.mybatis.common.ExecutorType;
import com.hdz.mybatis.executor.Executor;
import com.hdz.mybatis.executor.SimpleExecutor;
import com.hdz.mybatis.executor.paramter.DefaultParameterHandler;
import com.hdz.mybatis.executor.paramter.ParameterHandler;
import com.hdz.mybatis.executor.resultset.DefaultResultSetHandler;
import com.hdz.mybatis.executor.resultset.ResultSetHandler;
import com.hdz.mybatis.executor.statement.SimpleStatementHandler;
import com.hdz.mybatis.executor.statement.StatementHandler;
import com.hdz.mybatis.mapping.MappedStatement;
import com.hdz.mybatis.mapping.MapperRegistry;
import com.hdz.mybatis.session.inter.SqlSession;

import java.sql.ResultSet;
import java.util.*;

/**
 * @ClassName Configuration
 * @Description TODO
 * @Author 华达州
 * @Date 2021/8/19 11:52
 * @Version 1.0
 **/
public class Configuration {

    protected ExecutorType defaultExecutorType = ExecutorType.SIMPLE;


    public static Properties configProp = new Properties();

    protected final MapperRegistry mapperRegistry = new MapperRegistry(this);

    protected final Map<String, MappedStatement> mappedStatements = new HashMap<>();

    protected final Set<String> loadedResources = new HashSet<>();
    


    public static String getProperty(String key){
        return getProperty(key,"mapperLocation");
    }

    public static String getProperty(String key,String defaultValue) {
        return configProp.containsKey(key)?configProp.getProperty(key):defaultValue;
    }

    public void addMappedStatement(String key,MappedStatement mappedStatement){
        this.mappedStatements.put(key, mappedStatement);
    }

    public MappedStatement getMappedStatement(String statementId){
        return this.mappedStatements.get(statementId);
    }

    public <T> void addMapper(Class<T> type){
        this.mapperRegistry.addMapper(type);
    }

    public <T> boolean hasMapper(Class<T> type){
        return this.mapperRegistry.hasMapper(type);
    }

    public <T> T getMapper(Class<T> type, SqlSession sqlSession){
        return this.mapperRegistry.getMapper(type, sqlSession);
    }
    
    public void addLoadedResource(String resource){
        this.loadedResources.add(resource);
    }
    
    public boolean isLoadedResource(String resource){
        return this.loadedResources.contains(resource);
    }

    public Executor newExecutor(){
        return this.newExecutor(this.defaultExecutorType);
    }

    public Executor newExecutor(ExecutorType executorType){
        if(ExecutorType.REUSE==executorType){

        }
        else if(ExecutorType.BATCH==executorType){

        }
        else if(ExecutorType.SIMPLE==executorType){
            return new SimpleExecutor(this);
        }
        return new SimpleExecutor(this);
    }


    public StatementHandler newStatementHandler(MappedStatement ms) {
        return new SimpleStatementHandler(ms);
    }
    
    public ParameterHandler newParameterHandler(Object parameter){
        return new DefaultParameterHandler(parameter);
    }
    
    public ResultSetHandler newDefaultResultSetHandler(MappedStatement ms){
        return new DefaultResultSetHandler(ms);
    }

}
