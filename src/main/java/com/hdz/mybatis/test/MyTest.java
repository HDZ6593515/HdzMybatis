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
 * @Description TODO
 * @Author 华达州
 * @Date 2021/8/19 17:15
 * @Version 1.0
 **/
public class MyTest {
    
    private SqlSessionFactory factory;
    
    private SqlSession sqlSession;
    
    
    
    @Before
    public void before(){
        factory = new SqlSessionFactoryBuilder().build("mybatis-config.properties");
        sqlSession = factory.openSession();
    }
    

    @Test
    public void test01(){
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<User> users = mapper.selectAll();
        for (User user : users) {
            System.out.println(user);
        }
    }
    
    @Test
    public void testCount(){
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        int i = mapper.selectCount();
        System.out.println(i);
    }
    
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
