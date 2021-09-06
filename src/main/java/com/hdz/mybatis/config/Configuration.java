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
import com.hdz.mybatis.plugin.InterceptorChain;
import com.hdz.mybatis.plugin.inter.Interceptor;
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


    /**
     * 默认执行器类型
     */
    protected ExecutorType defaultExecutorType = ExecutorType.SIMPLE;


    /**
     * properties配置文件
     */
    public static Properties configProp = new Properties();

    /**
     * mapper注册器
     */
    protected final MapperRegistry mapperRegistry = new MapperRegistry(this);

    /**
     * 每一个select|update|insert|delete标签都对应一个唯一的mappedStatement
     * 
     * mapperStatement映射，key--》全限定类名+方法名，value--》mappedStatement
     */
    protected final Map<String, MappedStatement> mappedStatements = new HashMap<>();


    /**
     * 已加载的资源，标识是否已经解析xml文件或者mapper接口
     */
    protected final Set<String> loadedResources = new HashSet<>();


    /**
     * 拦截器链
     */
    protected final InterceptorChain interceptorChain = new InterceptorChain();
    


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
    
    public void addInterceptor(Interceptor interceptor){
        interceptorChain.addInterceptor(interceptor);
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
        Executor executor = null;
        if(ExecutorType.REUSE==executorType){

        }
        else if(ExecutorType.BATCH==executorType){

        }
        else if(ExecutorType.SIMPLE==executorType){
            executor =  new SimpleExecutor(this);
            
        }
        //代理所有插件，返回一个被所有插件拦截的代理对象
        executor = (Executor) interceptorChain.pluginAll(executor);
        return executor;
    }


    public StatementHandler newStatementHandler(MappedStatement ms) {
        StatementHandler statementHandler  = new SimpleStatementHandler(ms);
        statementHandler = (StatementHandler) interceptorChain.pluginAll(statementHandler);
        return statementHandler;
    }
    
    public ParameterHandler newParameterHandler(Object parameter){
        ParameterHandler parameterHandler = new DefaultParameterHandler(parameter);
        parameterHandler = (ParameterHandler) interceptorChain.pluginAll(parameterHandler);
        return parameterHandler;
    }
    
    public ResultSetHandler newDefaultResultSetHandler(MappedStatement ms){
        ResultSetHandler resultSetHandler = new DefaultResultSetHandler(ms);
        resultSetHandler = (ResultSetHandler) interceptorChain.pluginAll(resultSetHandler);
        return resultSetHandler;
    }

}
