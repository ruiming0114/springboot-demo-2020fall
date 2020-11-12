package com.example.demo.controller;

import com.example.demo.pojo.JsonResult;
import com.example.demo.pojo.User;
import com.example.demo.service.UserService;
import com.example.demo.utils.JwtUtils;
import com.example.demo.utils.RedisUtils;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;

@RestController
@ApiOperation("用户控制类")
public class UserController {

    @Resource
    private UserService userService;

    @Autowired
    private RedisUtils redisUtils;

    @ApiOperation("注册账户")
    @PostMapping("/register")
    public JsonResult<Object> register(@ApiParam("邮箱") @RequestParam("email") String email,@ApiParam("密码") @RequestParam("password") String password) throws MessagingException {
        if (userService.register(email,password)!=0){
            return new JsonResult<>(1,"The email address has been registered");
        }
        else {
            return new JsonResult<>();
        }
    }

    @ApiOperation("激活账户")
    @PostMapping("/activate")
    public JsonResult<Object> activate(@ApiParam("激活码") @RequestParam("activateCode") String activateCode){
        int res = userService.confirm(activateCode);
        if (res == 0) {
            return new JsonResult<>();
        }
        else if (res == 1){
            return new JsonResult<>(1,"Invalid activate");
        }
        else {
            return new JsonResult<>(2,"The email address has been registered");
        }
    }

    @ApiOperation("登录")
    @PostMapping("/login")
    public JsonResult<Object> login(@RequestParam("email") String email, @RequestParam("password") String password,HttpServletResponse response){
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(email,password);
        try {
            subject.login(usernamePasswordToken);
            User user = userService.getUserByEmail(email);
            long currentTimeMillis = System.currentTimeMillis();
            String token= JwtUtils.createToken(user.getEmail(),currentTimeMillis);
            redisUtils.set(email,currentTimeMillis,60*30);
            response.setHeader("Authorization",token);
            response.setHeader("Access-Control-Expose-Headers", "Authorization");
            return new JsonResult<>(token,0,"success");
        }catch (UnknownAccountException e){
            return new JsonResult<>(1,"The email address is not existed");
        }catch (IncorrectCredentialsException e){
            return new JsonResult<>(2,"Wrong password");
        }
    }

    @GetMapping("/host")
    public JsonResult<Object> host(){
        return new JsonResult<>();
    }

    @RequestMapping("/unauthorized/{msg}")
    public JsonResult<Object> un(@PathVariable("msg") String msg){
        return new JsonResult<>(1,msg.replace("-"," "));
    }

}
