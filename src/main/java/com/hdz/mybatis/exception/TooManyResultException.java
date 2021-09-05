package com.hdz.mybatis.exception;

/**
 * @ClassName TooManyResultException
 * @Description TODO
 * @Author 华达州
 * @Date 2021/8/19 18:06
 * @Version 1.0
 **/
public class TooManyResultException extends RuntimeException {

    public TooManyResultException(){

    }

    public TooManyResultException(String message){
        super(message);
    }
}
