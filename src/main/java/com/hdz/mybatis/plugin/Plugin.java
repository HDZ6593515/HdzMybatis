package com.hdz.mybatis.plugin;

import com.hdz.mybatis.plugin.annotation.Interceptors;
import com.hdz.mybatis.plugin.annotation.Signature;
import com.hdz.mybatis.plugin.inter.Interceptor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.*;

/**
 * @ClassName Plugin
 * @Description TODO
 * @Author 华达州
 * @Date 2021/9/5 15:23
 * @Version 1.0
 **/
public class Plugin implements InvocationHandler {
    
    private final Object target;
    
    private final Interceptor interceptor;
    
    private final Map<Class<?>, Set<Method>> signatureMap;

    public Plugin(Object target, Interceptor interceptor, Map<Class<?>, Set<Method>> signatureMap) {
        this.target = target;
        this.interceptor = interceptor;
        this.signatureMap = signatureMap;
    }
    
    public static Object wrap(Object target,Interceptor interceptor){
        Interceptors interceptors = interceptor.getClass().getAnnotation(Interceptors.class);
        if(interceptors==null){
            throw new RuntimeException("该拦截器没有@Interceptors注解");
        }
        
        Map<Class<?>,Set<Method>> signatureMap = new HashMap<>();
        
        Signature[] signatures = interceptors.value();
        for (Signature signature : signatures) {
            Set<Method> methods = signatureMap.getOrDefault(signature.type(), new HashSet<>());
            try {
                Method method = signature.type().getMethod(signature.method(), signature.args());
                methods.add(method);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }

        Class<?> targetClass = target.getClass();
        Class<?>[] interfaces = targetClass.getInterfaces();
        List<Class<?>> list = new ArrayList<>();
        for (Class<?> anInterface : interfaces) {
            if(signatureMap.containsKey(anInterface)){
                list.add(anInterface);
            }
        }
        if(list.size()>0){
            return Proxy.newProxyInstance(
                    targetClass.getClassLoader(),
                    (Class<?>[]) list.toArray(),
                    new Plugin(target,interceptor,signatureMap));
        }
        return target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Set<Method> methods = signatureMap.get(method.getDeclaringClass());
        if(methods!=null&&methods.contains(method)){
            return interceptor.intercept(new Invocation(target,method,args));
        }
        return method.invoke(target,args);
    }
}
