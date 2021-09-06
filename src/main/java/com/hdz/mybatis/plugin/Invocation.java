package com.hdz.mybatis.plugin;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @ClassName Invocation
 * @Description TODO
 * @Author 华达州
 * @Date 2021/9/5 16:34
 * @Version 1.0
 **/
public class Invocation {
    
    private Object target;
    
    private Method method;
    
    private Object[] args;

    public Invocation(Object target, Method method, Object[] args) {
        this.target = target;
        this.method = method;
        this.args = args;
    }


    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }
    
    public Object proceed() throws InvocationTargetException, IllegalAccessException {
        return method.invoke(target,args);
    }
}
