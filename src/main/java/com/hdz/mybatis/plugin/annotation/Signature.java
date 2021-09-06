package com.hdz.mybatis.plugin.annotation;

import java.lang.annotation.*;

/**
 * @AnnotationName Signature
 * @Description TODO
 * @Author 华达州
 * @Date 2021/9/5 16:40
 * @Version 1.0
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({})
public @interface Signature {
    
    Class<?> type();
    
    String method();
    
    Class<?>[] args();
    
}
