package com.hdz.mybatis.executor.resultset;

import java.sql.ResultSet;
import java.util.List;

/**
 * @InterfaceName ResultSetHandler
 * @Description TODO
 * @Author 华达州
 * @Date 2021/8/19 19:19
 * @Version 1.0
 **/
public interface ResultSetHandler {
    <E> List<E> handleResultSet(ResultSet resultSet);
}
