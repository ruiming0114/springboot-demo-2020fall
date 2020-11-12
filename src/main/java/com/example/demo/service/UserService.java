package com.example.demo.service;

import com.example.demo.pojo.User;

import javax.mail.MessagingException;

public interface UserService {

    int register(String email,String password) throws MessagingException;
    int confirm(String activateCode);

    User getUserByEmail(String email);
}
