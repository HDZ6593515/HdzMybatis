package com.hdz.mybatis.session;

import com.hdz.mybatis.common.Constant;
import com.hdz.mybatis.config.Configuration;
import com.hdz.mybatis.session.inter.SqlSession;
import com.hdz.mybatis.session.inter.SqlSessionFactory;
import com.hdz.mybatis.utils.XmlUtil;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * @ClassName DefaultSqlSessionFactory
 * @Description TODO
 * @Author 华达州
 * @Date 2021/8/19 11:57
 * @Version 1.0
 **/
public class DefaultSqlSessionFactory implements SqlSessionFactory {

    private final Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration){
        this.configuration = configuration;
        loadMapperInfo(Configuration.getProperty(Constant.MAPPER_LOCATION).replaceAll("\\.","/"));
    }

    @Override
    public SqlSession openSession() {
        return new DefaultSqlSession(this.configuration);
    }

    //加载XXXmapper.xml文件
    private void loadMapperInfo(String mapperLocation){
        URL resource = DefaultSqlSessionFactory.class.getClassLoader().getResource(mapperLocation);
        File mapperDir = new File(resource.getFile());

        if(mapperDir.isDirectory()){

            File[] files = mapperDir.listFiles();
            for (File file : files) {
                if(file.isDirectory()){
                    loadMapperInfo(mapperLocation+"/"+file.getName());
                }
                else if (file.getName().endsWith(Constant.MAPPER_FILE_SUFFIX)){
                    if(!this.configuration.isLoadedResource(file.getName())){
                        this.configuration.addLoadedResource(file.getName());
                        //解析xml文件
                        XmlUtil.readMapperXml(file,this.configuration);
                    }
                }
                else{
                    try {
                        String temp = file.getPath().replace("\\", ".");
                        temp = temp.substring(temp.indexOf(".classes")+9,temp.lastIndexOf(".class"));
                        Class<?> type = Class.forName(temp);
                        if(type!=null){
                            this.configuration.addMapper(type);
                        }
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
        else{
            XmlUtil.readMapperXml(mapperDir,this.configuration);
        }
    }
}
