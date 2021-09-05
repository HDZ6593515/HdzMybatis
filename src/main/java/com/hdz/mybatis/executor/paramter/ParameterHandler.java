package com.hdz.mybatis.executor.paramter;

import java.sql.PreparedStatement;

/**
 * @InterfaceName ParameterHandler
 * @Description TODO
 * @Author 华达州
 * @Date 2021/8/19 19:18
 * @Version 1.0
 **/
public interface ParameterHandler {
    void setParameters(PreparedStatement preparedStatement);
}
