package com.hdz.mybatis.common;

/**
 * @EnumName ExecutorType
 * @Description 执行器类型
 * @Author 华达州
 * @Date 2021/8/19 20:11
 * @Version 1.0
 **/
public enum ExecutorType {
    SIMPLE,
    REUSE,
    BATCH;
    private ExecutorType() {
    }
}
