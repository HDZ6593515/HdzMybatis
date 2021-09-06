package com.hdz.mybatis.plugin.annotation;

import java.lang.annotation.*;

/**
 * @AnnotationName Interceptors
 * @Description TODO
 * @Author 华达州
 * @Date 2021/9/5 16:38
 * @Version 1.0
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Interceptors {
    
    Signature[] value();
    
}
