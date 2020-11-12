package com.example.demo.config;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.example.demo.pojo.JWTToken;
import com.example.demo.pojo.User;
import com.example.demo.service.UserService;
import com.example.demo.utils.JwtUtils;
import com.example.demo.utils.RedisUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

public class JWTRealm extends AuthorizingRealm {

    @Autowired
    UserService userService;

    @Autowired
    RedisUtils redisUtils;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String jwt = (String) token.getCredentials();
        String email = null;
        try {
            email = JwtUtils.getEmail(jwt);
        }catch (Exception e){
            throw new AuthenticationException("illegal token");
        }
        if (email == null){
            throw new AuthenticationException("empty email");
        }
        User user = userService.getUserByEmail(email);
        if (user == null){
            throw new AuthenticationException("user doesn't exists");
        }
        if(redisUtils.hasKey(email)){
            if (!JwtUtils.verify(jwt)){
                throw new TokenExpiredException("failed");
            }
            else {
                long current = (long) redisUtils.get(email);
                if (current == JwtUtils.getExpire(jwt)){
                    return new SimpleAuthenticationInfo(jwt,jwt,"JWTRealm");
                }
                else {
                    throw new AuthenticationException("token out of time");
                }
            }
        }
        else {
            throw new AuthenticationException("failed");
        }
    }
}
