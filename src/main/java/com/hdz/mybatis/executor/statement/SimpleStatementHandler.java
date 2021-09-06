package com.hdz.mybatis.executor.statement;

import com.hdz.mybatis.mapping.MappedStatement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @ClassName SimpleStatementHandler
 * @Description TODO
 * @Author 华达州
 * @Date 2021/8/19 19:17
 * @Version 1.0
 **/
public class SimpleStatementHandler implements StatementHandler {

    private MappedStatement mappedStatement;

    public SimpleStatementHandler(MappedStatement mappedStatement){
        this.mappedStatement = mappedStatement;
    }


    @Override
    public PreparedStatement prepare(Connection connection) {
        String sql = this.mappedStatement.getSql();
        PreparedStatement preparedStatement = null;
        try {
            String prepareSql = parseSql(sql);
            System.out.println("====> Preparing："+prepareSql);
            preparedStatement = connection.prepareStatement(prepareSql);
            return preparedStatement;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ResultSet query(PreparedStatement preparedStatement) {
        ResultSet resultSet = null;
        try {
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    @Override
    public int update(PreparedStatement preparedStatement) {
        try {
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 该方法把sql语句中的 #{xxx} 换成?
     * @author 华达州
     * @date 2021/9/6 20:15
     * @param originSql 
     * @return java.lang.String
     */
    @Override
    public String parseSql(String originSql) {
        StringBuilder stringBuilder = new StringBuilder();
        int start = 0;
        int left = 0;
        int right = 0;
        char[] chars = originSql.toCharArray();
        while(start<chars.length){
            left = start;
            while(start+1<chars.length&&(chars[start]!='#'||chars[start+1]!='{')){
                start++;
            }
            right = start;
            start += 2;
            if(right==chars.length-1){
                return originSql;
            }
            else{
                stringBuilder.append(chars,left,right-left).append("?");
            }
            while(start<chars.length&&chars[start]!='}'){
                start++;
            }
            start++;
        }
        return stringBuilder.toString();
    }

    
}
