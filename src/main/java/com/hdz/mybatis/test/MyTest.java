package com.hdz.mybatis.test;

import com.hdz.mybatis.session.SqlSessionFactoryBuilder;
import com.hdz.mybatis.session.inter.SqlSession;
import com.hdz.mybatis.session.inter.SqlSessionFactory;
import com.hdz.mybatis.test.entity.User;
import com.hdz.mybatis.test.mapper.UserMapper;
import com.hdz.mybatis.test.pageHelp.PageHelp;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * @ClassName MyMain
 * @Description 测试类
 * @Author 华达州
 * @Date 2021/8/19 17:15
 * @Version 1.0
 **/
public class MyTest {


    /**
     * 工厂
     */
    private SqlSessionFactory factory;


    /**
     * 声明sqlSession会话
     */
    private SqlSession sqlSession;


    /**
     * 执行测试代码之前执行
     */
    @Before
    public void before(){
        /**
         * 读取配置文件
         * 在读取的过程中，会获取相关配置并完成一些初始化，会读取xml配置文件和Mapper类去构建MappedStatement等等
         */
        factory = new SqlSessionFactoryBuilder().build("mybatis-config.properties");
        /**
         * 开启一个会话
         */
        sqlSession = factory.openSession();
    }


    /**
     * 测试查询所有记录
     */
    @Test
    public void testFindAll(){
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<User> users = mapper.selectAll();
        for (User user : users) {
            System.out.println(user);
        }
    }


    /**
     * 测试聚合函数
     */
    @Test
    public void testCount(){
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        int i = mapper.selectCount();
        System.out.println(i);
    }

    /**
     * 测试插件
     */
    @Test
    public void testPlugin(){
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        PageHelp.startPage(0,3);
        List<User> users = mapper.selectAll();
        for (User user : users) {
            System.out.println(user);
        }
    }
    
    

    public static String parseSql(String originSql) {
        StringBuilder stringBuilder = new StringBuilder();
        int start = 0;
        int left = 0;
        int right = 0;
        char[] chars = originSql.toCharArray();
        while(start<chars.length){
            left = start;
            while(start+1<chars.length&&(chars[start]!='#'||chars[start+1]!='{')){
                start++;
            }
            right = start;
            start += 2;
            stringBuilder.append(chars,left,right-left).append("?");
            while(start<chars.length&&chars[start]!='}'){
                start++;
            }
            start++;
        }
        return stringBuilder.toString();
    }

}
