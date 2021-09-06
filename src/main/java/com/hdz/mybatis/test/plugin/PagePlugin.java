package com.hdz.mybatis.test.plugin;

import com.hdz.mybatis.executor.Executor;
import com.hdz.mybatis.mapping.MappedStatement;
import com.hdz.mybatis.plugin.Invocation;
import com.hdz.mybatis.plugin.Plugin;
import com.hdz.mybatis.plugin.annotation.Interceptors;
import com.hdz.mybatis.plugin.annotation.Signature;
import com.hdz.mybatis.plugin.inter.Interceptor;
import com.hdz.mybatis.test.pageHelp.Page;
import com.hdz.mybatis.test.pageHelp.PageHelp;

import java.lang.reflect.InvocationTargetException;

/**
 * @ClassName PagePlugin
 * @Description TODO
 * @Author 华达州
 * @Date 2021/9/5 19:28
 * @Version 1.0
 **/
@Interceptors({
        @Signature(type = Executor.class,method = "query",args = {MappedStatement.class,Object.class})
})
public class PagePlugin implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) {
        try {
            Executor executor = (Executor)invocation.getTarget();
            Object[] args = invocation.getArgs();
            MappedStatement ms = (MappedStatement) args[0];
            Object parameter = args[1];
            Page page = PageHelp.getPage();
            if(page!=null){
                StringBuilder totalBuilder = new StringBuilder();
                totalBuilder.append("select count(*) from (").append(ms.getSql()).append(")");
                ms.setSql(totalBuilder.toString());
                page.setTotal((long)executor.query(ms,parameter).get(0));
                
                StringBuilder sqlBuilder = new StringBuilder(ms.getSql());
                sqlBuilder.append(" limit ").append(page.getOffset()).append(",").append(page.getPageNum());
                ms.setSql(sqlBuilder.toString());
                System.out.println(page);
                return executor.query(ms,parameter);
            }
            return invocation.proceed();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            PageHelp.removePage();
        }
        return null;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target,this);
    }
}
