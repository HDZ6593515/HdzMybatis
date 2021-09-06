package com.hdz.mybatis.session;

import com.hdz.mybatis.config.Configuration;
import com.hdz.mybatis.session.inter.SqlSessionFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * sql会话工厂建造者
 *
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
            //加载properties配置文件
            Configuration.configProp.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        //DefaultSqlSessionFactory的构造函数会加载配置文件，可以进去看看
        return new DefaultSqlSessionFactory(new Configuration());
    }
}
