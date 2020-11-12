package com.example.demo.mapper;

import com.example.demo.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Mapper
@Repository
public interface UserMapper {

    /*
        多个参数可以用map封装，以免调用方法时参数顺序错误，也可以直接列举参数
     */
    void addUser(Map<String,Object> map);
    User getUserById(int uid);
    User getUserByEmail(String email);
}
