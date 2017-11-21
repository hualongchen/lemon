package com.lemon.chen.util.email;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;



//@Component
public class EmailUtil {


    @Autowired
    private JavaMailSender   mailSender ;

    @Autowired
    private EmailConfig    config ;


    /**
     * 发送简单的邮件
     * @param sendTo
     * @param titel
     * @param content
     */
    public void sendSimpleMail(String sendTo, String titel, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(config.getAccount());
        message.setTo(sendTo);
        message.setSubject(titel);
        message.setText(content);
        mailSender.send(message);
    }






}
