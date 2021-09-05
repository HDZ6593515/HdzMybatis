package com.hdz.mybatis.session.inter;

import com.hdz.mybatis.config.Configuration;

/**
 * @InterfaceName DefaultSessionFactory
 * @Description TODO
 * @Author 华达州
 * @Date 2021/8/19 11:56
 * @Version 1.0
 **/
public interface SqlSessionFactory {

    SqlSession openSession();

}
