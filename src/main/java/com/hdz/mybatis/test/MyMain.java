package com.hdz.mybatis.test;

import com.hdz.mybatis.session.SqlSessionFactoryBuilder;
import com.hdz.mybatis.session.inter.SqlSession;
import com.hdz.mybatis.session.inter.SqlSessionFactory;
import com.hdz.mybatis.test.entity.User;
import com.hdz.mybatis.test.mapper.UserMapper;
import org.junit.Test;

import java.util.List;

/**
 * @ClassName MyMain
 * @Description TODO
 * @Author 华达州
 * @Date 2021/8/19 17:15
 * @Version 1.0
 **/
public class MyMain {

    @Test
    public void test01(){
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build("mybatis-config.properties");
        SqlSession sqlSession = factory.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        System.out.println(mapper.selectById(1));
        System.out.println(mapper.updateById("hahhaha", 1));
        System.out.println(mapper.selectById(1));


        List<User> users = mapper.selectAll();
        System.out.println(users);
    }
    
    
    public static void main(String[] args) {
        




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
