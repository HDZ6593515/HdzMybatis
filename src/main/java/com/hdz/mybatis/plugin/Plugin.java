package com.hdz.mybatis.plugin;

import com.hdz.mybatis.annotation.Select;
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
    
    /**
     * 包装插件拦截器拦截的类和方法
     * （模仿mybatis源码，只提供Executor、StatementHandler、ParameterHandler、ResultSetHandler四种）
     * @author 华达州
     * @date 2021/9/6 19:25
     * @param target
     * @param interceptor 
     * @return java.lang.Object
     */
    public static Object wrap(Object target,Interceptor interceptor){
        Interceptors interceptors = interceptor.getClass().getAnnotation(Interceptors.class);
        if(interceptors==null){
            throw new RuntimeException("该拦截器没有@Interceptors注解");
        }
        
        Map<Class<?>,Set<Method>> signatureMap = new HashMap<>();
        
        Signature[] signatures = interceptors.value();
        for (Signature signature : signatures) {
            try {
                Set<Method> methods;
                if(signatureMap.get(signature.type())==null){
                    methods = new HashSet<>();
                    signatureMap.put(signature.type(),methods);
                }
                else{
                    methods = signatureMap.get(signature.type());
                }
                Method method = signature.type().getMethod(signature.method(), signature.args());
                methods.add(method);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }

        Class<?> targetClass = target.getClass();
        Class<?>[] interfaces = targetClass.getSuperclass().getInterfaces();
        List<Class<?>> list = new ArrayList<>();
        for (Class<?> anInterface : interfaces) {
            if(signatureMap.containsKey(anInterface)){
                list.add(anInterface);
            }
        }
        if(list.size()>0){
            //如果需要拦截，则返回代理对象
            return Proxy.newProxyInstance(
                    targetClass.getClassLoader(),
                    list.toArray(new Class<?>[list.size()]),
                    new Plugin(target,interceptor,signatureMap));
        }
        return target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Set<Method> methods = signatureMap.get(method.getDeclaringClass());
        //判断是不是需要拦截的方法
        if(methods!=null&&methods.contains(method)){
            //执行拦截逻辑，由用户实现
            return interceptor.intercept(new Invocation(target,method,args));
        }
        return method.invoke(target,args);
    }
}
