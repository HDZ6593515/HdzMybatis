package com.hdz.mybatis.test.mapper;

import com.hdz.mybatis.annotation.Select;
import com.hdz.mybatis.annotation.Update;
import com.hdz.mybatis.test.entity.User;

import java.util.List;

/**
 * @InterfaceName UserDao
 * @Description TODO
 * @Author 华达州
 * @Date 2021/8/19 17:18
 * @Version 1.0
 **/

public interface UserMapper {

    @Select("select * from user where id=2")
    User selectById(int id);
    
    @Select("select * from user")
    List<User> selectAll();
    
    @Update("update user set name=#{name} where id=#{id}")
    int updateById(String name,int id);
    
}
