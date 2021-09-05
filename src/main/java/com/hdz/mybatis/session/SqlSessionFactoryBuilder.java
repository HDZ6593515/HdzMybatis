package com.hdz.mybatis.session;

import com.hdz.mybatis.config.Configuration;
import com.hdz.mybatis.session.inter.SqlSessionFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * @ClassName SqlSessionFactoryBuilder
 * @Description TODO
 * @Author 华达州
 * @Date 2021/8/19 11:57
 * @Version 1.0
 **/
public class SqlSessionFactoryBuilder {
    public SqlSessionFactory build(String propFileName){
        InputStream inputStream = SqlSessionFactoryBuilder.class.getClassLoader().getResourceAsStream(propFileName);
        return this.build(inputStream);
    }

    public SqlSessionFactory build(InputStream inputStream){
        try {
            Configuration.configProp.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new DefaultSqlSessionFactory(new Configuration());
    }
}
