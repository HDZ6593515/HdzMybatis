package com.hdz.mybatis.executor.statement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @InterfaceName StatementHandler
 * @Description TODO
 * @Author 华达州
 * @Date 2021/8/19 19:17
 * @Version 1.0
 **/
public interface StatementHandler {

    PreparedStatement prepare(Connection connection);

    ResultSet query(PreparedStatement preparedStatement);

    String parseSql(String originSql);

    int update(PreparedStatement preparedStatement);
}
