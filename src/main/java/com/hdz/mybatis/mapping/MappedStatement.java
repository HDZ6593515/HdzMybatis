package com.hdz.mybatis.mapping;

import com.hdz.mybatis.common.Constant;
import com.hdz.mybatis.config.Configuration;

/**
 * @ClassName MappedStatement
 * @Description TODO
 * @Author 华达州
 * @Date 2021/8/19 13:24
 * @Version 1.0
 **/
public class MappedStatement {

    private String namespace;

    private String sqlId;

    private String sql;

    private String resultType;

    private String sqlCommandType;

    private Configuration configuration;

    public MappedStatement(){

    }

    public MappedStatement(Configuration configuration){
        this.configuration = configuration;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getSqlId() {
        return sqlId;
    }

    public void setSqlId(String sqlId) {
        this.sqlId = sqlId;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public String getSqlCommandType() {
        return sqlCommandType;
    }

    public void setSqlCommandType(String sqlCommandType) {
        this.sqlCommandType = sqlCommandType;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }
}
