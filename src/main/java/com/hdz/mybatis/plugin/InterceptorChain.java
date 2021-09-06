package com.hdz.mybatis.plugin;

import com.hdz.mybatis.plugin.inter.Interceptor;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName InterceptorChain
 * @Description TODO
 * @Author 华达州
 * @Date 2021/9/5 16:27
 * @Version 1.0
 **/
public class InterceptorChain {
    
    private final List<Interceptor> interceptors = new ArrayList<>();
    
    public Object pluginAll(Object target){
        for (Interceptor interceptor : interceptors) {
            target = interceptor.plugin(target);
        }
        return target;
    }
    
    public void addInterceptor(Interceptor interceptor){
        interceptors.add(interceptor);
    }
    
    public List<Interceptor> getInterceptors(){
        return interceptors;
    }
    
    
}
