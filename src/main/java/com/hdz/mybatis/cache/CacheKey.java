package com.hdz.mybatis.cache;

import java.util.Arrays;
import java.util.Objects;

/**
 * @ClassName CacheKey
 * @Description 一级缓存key
 * @Author 华达州
 * @Date 2021/8/28 19:22
 * @Version 1.0
 **/
public class CacheKey {
    
    
    /**
     * statementId = 全限定类名+方法名
     */
    private String statementId;


    /**
     * sql语句
     */
    private String sql;

    /**
     * 参数列表
     */
    private Object[] parameter;

    public String getStatementId() {
        return statementId;
    }

    public void setStatementId(String statementId) {
        this.statementId = statementId;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public Object[] getParameter() {
        return parameter;
    }

    public void setParameter(Object[] parameter) {
        this.parameter = parameter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CacheKey cacheKey = (CacheKey) o;
        return statementId.equals(cacheKey.statementId) &&
                sql.equals(cacheKey.sql) &&
                Arrays.equals(parameter, cacheKey.parameter);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(statementId, sql);
        result = 31 * result + Arrays.hashCode(parameter);
        return result;
    }
}
