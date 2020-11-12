package com.example.demo.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class SendEmailUtils {

    @Value("${spring.mail.username}")
    private String sender;

    @Value("${myclient.host}")
    private String host;

    @Value("${myclient.port}")
    private String port;

    @Value("${myclient.activate}")
    private String activateStr;

    @Resource
    private JavaMailSenderImpl javaMailSender;

    @Async
    public void SendRegisterEmail(String receiver, String code) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String text = "请点击以下链接确认注册，激活账户\n"+
                "http://"+host+":"+port+"/#/"+activateStr+"/"+code+"\n"
                +"激活链接将在"+df.format(new Date(new Date().getTime()+(long)5*60*1000))+"失效";
        helper.setFrom(sender);
        helper.setTo(receiver);
        helper.setSubject("Register Confirm from BUAASE-2020");
        helper.setText(text);
        javaMailSender.send(message);
    }
}
