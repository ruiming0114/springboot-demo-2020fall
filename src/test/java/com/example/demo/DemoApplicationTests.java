package com.example.demo;

import com.example.demo.controller.UserController;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.UserService;
import com.example.demo.utils.JwtUtils;
import com.example.demo.utils.RedisUtils;
import com.example.demo.utils.SendEmailUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.mail.MessagingException;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    private UserService userService;

    @Autowired
    private SendEmailUtils sendEmailUtils;

    @Autowired
    private RedisUtils redisUtils;

    @Test
    void contextLoads() {
    }
}
