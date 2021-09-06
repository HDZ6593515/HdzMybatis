package com.hdz.mybatis.session;

import com.hdz.mybatis.common.Constant;
import com.hdz.mybatis.config.Configuration;
import com.hdz.mybatis.plugin.inter.Interceptor;
import com.hdz.mybatis.session.inter.SqlSession;
import com.hdz.mybatis.session.inter.SqlSessionFactory;
import com.hdz.mybatis.utils.XmlUtil;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * @ClassName DefaultSqlSessionFactory
 * @Description 默认会话工厂类
 * @Author 华达州
 * @Date 2021/8/19 11:57
 * @Version 1.0
 **/
public class DefaultSqlSessionFactory implements SqlSessionFactory {

    private final Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration){
        this.configuration = configuration;
        /**
         * 加载mapper文件，这里规定xml文件和mapper接口必须在同一包下
         */
        loadMapperInfo(Configuration.getProperty(Constant.MAPPER_LOCATION).replaceAll("\\.","/"));

        /**
         * 加载插件
         */
        loadAllPlugins(Configuration.getProperty(Constant.PLUGIN_LOCATION).replaceAll("\\.","/"));
    }

    @Override
    public SqlSession openSession() {
        
        //默认返回一个DefaultSqlSession对象，创建过程中会给定一个默认的执行器SimpleExecutor，进去看看
        return new DefaultSqlSession(this.configuration);
    }
    
    /**
     * 加载xml配置文件
     * @author 华达州
     * @date 2021/9/6 19:17
     * @param mapperLocation 
     */
    private void loadMapperInfo(String mapperLocation){
        URL resource = DefaultSqlSessionFactory.class.getClassLoader().getResource(mapperLocation);
        File mapperDir = new File(resource.getFile());

        //判断给出是一个目录还是单独一个xml文件
        if(mapperDir.isDirectory()){

            File[] files = mapperDir.listFiles();
            for (File file : files) {
                //递归遍历文件夹内容
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
    
    
    /**
     * 加载插件
     * @author 华达州
     * @date 2021/9/6 19:17
     * @param pluginLocation 
     */
    private void loadAllPlugins(String pluginLocation){
        URL resource = DefaultSqlSessionFactory.class.getClassLoader().getResource(pluginLocation);
        try {
            //判断给出的是单个插件类还是一个目录
            if(resource==null){
                String pluginClass = pluginLocation.replace("/",".");
                Class<?> aClass = Class.forName(pluginClass);
                Interceptor interceptor = (Interceptor)aClass.newInstance();
                //把插件拦截器加入拦截器链当中
                configuration.addInterceptor(interceptor);
            }
            else{
                File pluginFile = new File(resource.getFile());
                File[] files = pluginFile.listFiles();
                for (File file : files) {
                    String clazz = pluginLocation.replace("/",".")+"."+file.getName().substring(0,file.getName().indexOf(".class"));
                    System.out.println(clazz);
                    Class<?> aClass = Class.forName(clazz);
                    Interceptor interceptor = (Interceptor)aClass.newInstance();
                    //把插件拦截器加入拦截器链当中
                    configuration.addInterceptor(interceptor);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
