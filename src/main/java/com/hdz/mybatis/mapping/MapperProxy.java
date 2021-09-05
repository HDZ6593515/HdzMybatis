package com.hdz.mybatis.mapping;

import com.hdz.mybatis.session.inter.SqlSession;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Collection;

/**
 * @ClassName MapperProxy
 * @Description TODO
 * @Author 华达州
 * @Date 2021/8/19 16:47
 * @Version 1.0
 **/
public class MapperProxy<T> implements InvocationHandler {

    private final Class<T> mapperInterface;

    private final SqlSession sqlSession;

    public MapperProxy(SqlSession sqlSession,Class<T> mapperInterface){
        this.sqlSession = sqlSession;
        this.mapperInterface = mapperInterface;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        if(Object.class.equals(method.getDeclaringClass())){
            return method.invoke(this,args);
        }
        return this.execute(method,args);
    }

    public Object execute(Method method,Object[] args){
        String statementId = this.mapperInterface.getName()+"."+method.getName(); 
        MappedStatement ms = this.sqlSession.getConfiguration().getMappedStatement(statementId);

        Object result = null;
        switch (ms.getSqlCommandType()){
            case "select":{
                Class<?> returnType = method.getReturnType();
                if(Collection.class.isAssignableFrom(returnType)){
                    result = sqlSession.selectList(statementId,args);
                }
                else{
                    result = sqlSession.selectOne(statementId,args);
                }
                break;
            }
            case "delete":{
                result = rowCountResult(method,sqlSession.delete(statementId,args));
                break;
            }
            case "update":{
                result = rowCountResult(method,sqlSession.update(statementId,args));
                break;
            }
            case "insert":{
                result = rowCountResult(method,sqlSession.insert(statementId,args));
                break;
            }
            default:{
                break;
            }
        }
        return result;

    }
    
    private Object rowCountResult(Method method,int rowCount){
        final Object result;
        if (void.class.equals(method.getReturnType())) {
            result = null;
        } else if (Integer.class.equals(method.getReturnType()) || Integer.TYPE.equals(method.getReturnType())) {
            result = rowCount;
        } else if (Long.class.equals(method.getReturnType()) || Long.TYPE.equals(method.getReturnType())) {
            result = (long)rowCount;
        } else if (Boolean.class.equals(method.getReturnType()) || Boolean.TYPE.equals(method.getReturnType())) {
            result = rowCount > 0;
        } else {
            throw new RuntimeException("Mapper method '" + this.mapperInterface.getName()+"."+method.getName()  + "' has an unsupported return type: " + method.getReturnType());
        }
        return result;
    }
}
