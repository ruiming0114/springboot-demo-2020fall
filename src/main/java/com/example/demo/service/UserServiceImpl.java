package com.example.demo.service;

import com.example.demo.mapper.UserMapper;
import com.example.demo.pojo.User;
import com.example.demo.utils.RedisUtils;
import com.example.demo.utils.SendEmailUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private SendEmailUtils sendEmailUtils;

    @Resource
    private RedisUtils redisUtils;

    @Override
    public int register(String email,String password) throws MessagingException {
        if (userMapper.getUserByEmail(email) != null){
            return 1;
        }
        String activateCode = UUID.randomUUID().toString().replace("-","");
        sendEmailUtils.SendRegisterEmail(email,activateCode);
        Map<String,Object> map = new HashMap<>();
        map.put("email",email);
        String npwd = new SimpleHash("MD5",password,email,2).toString();
        map.put("password",npwd);
        redisUtils.hPutAll(activateCode,map,300);
        return 0;
    }

    @Override
    public int confirm(String activateCode) {
        if (!redisUtils.hasKey(activateCode)){
            return 1;
        }
        Map<Object,Object> originMap = redisUtils.entries(activateCode);
        Map<String,Object> map = new HashMap<>();
        map.put("email",originMap.get("email"));
        map.put("password",originMap.get("password"));
        if (userMapper.getUserByEmail((String) map.get("email")) != null){
            return 2;
        }
        userMapper.addUser(map);
        redisUtils.delete(activateCode);
        return 0;
    }

    @Override
    public User getUserByEmail(String email) {
        return userMapper.getUserByEmail(email);
    }
}
