package com.hdz.mybatis.annotation;

import java.lang.annotation.*;

/**
 * @AnnotationName Select
 * @Description TODO
 * @Author 华达州
 * @Date 2021/8/25 11:22
 * @Version 1.0
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Insert {
    String value();
}
