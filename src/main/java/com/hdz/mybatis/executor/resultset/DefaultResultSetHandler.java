package com.hdz.mybatis.executor.resultset;

import com.hdz.mybatis.mapping.MappedStatement;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName DefaultResultSetHandler
 * @Description TODO
 * @Author 华达州
 * @Date 2021/8/19 19:20
 * @Version 1.0
 **/
public class DefaultResultSetHandler implements ResultSetHandler {

    private MappedStatement mappedStatement;

    public DefaultResultSetHandler(MappedStatement mappedStatement){
        this.mappedStatement = mappedStatement;
    }


    @Override
    public <E> List<E> handleResultSet(ResultSet resultSet) {
        if(resultSet==null){
            return null;
        }
        try {
            List<E> resultList = new ArrayList<>();
            while(resultSet.next()){
                Class<?> entityClass = Class.forName(mappedStatement.getResultType());
                E entity = (E) entityClass.newInstance();
                Field[] declaredFields = entityClass.getDeclaredFields();
                for (Field field : declaredFields) {
                    field.setAccessible(true);
                    Class<?> type = field.getType();
                    
                    if(String.class.equals(type)){
                        field.set(entity,resultSet.getString(field.getName()));
                    }
                    else if(int.class.equals(type)){
                        field.set(entity,resultSet.getInt(field.getName()));
                    }
                    //其他类型
                    else{
                        field.set(entity,resultSet.getObject(field.getName()));
                    }
                }
                resultList.add(entity);
            }
            return resultList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
