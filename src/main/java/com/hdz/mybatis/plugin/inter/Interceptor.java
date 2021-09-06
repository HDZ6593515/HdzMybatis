package com.hdz.mybatis.plugin.inter;

import com.hdz.mybatis.plugin.Invocation;
import com.hdz.mybatis.plugin.Plugin;

import javax.jws.Oneway;

/**
 * @InterfaceName Interceptor
 * @Description TODO
 * @Author 华达州
 * @Date 2021/9/5 16:24
 * @Version 1.0
 **/
public interface Interceptor {
    
    Object intercept(Invocation invocation);
    
    default Object plugin(Object target){
        return Plugin.wrap(target,this);
    }
    
}
