package com.hdz.mybatis.test.plugin;

import com.hdz.mybatis.executor.Executor;
import com.hdz.mybatis.executor.statement.StatementHandler;
import com.hdz.mybatis.plugin.Invocation;
import com.hdz.mybatis.plugin.annotation.Interceptors;
import com.hdz.mybatis.plugin.annotation.Signature;
import com.hdz.mybatis.plugin.inter.Interceptor;

import java.sql.Connection;

/**
 * @ClassName SqlLogPlugin
 * @Description TODO
 * @Author 华达州
 * @Date 2021/9/5 19:28
 * @Version 1.0
 **/
@Interceptors({
        @Signature(type = StatementHandler.class,method = "prepare",args = {Connection.class})
})
public class SqlLogPlugin implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) {
        return null;
    }

    @Override
    public Object plugin(Object target) {
        return null;
    }
}
