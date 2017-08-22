package com.lemon.chen.util.email;


import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class EmailConfig {

    @Value("${stmp.host}")
    private String host;

    @Value("${stmp.account}")
    private String account;
    @Value("${stmp.password}")
    private String password;
    @Value("${mail.smtp.auth}")
    private String auth;
    @Value("${mail.smtp.timeout}")
    private long timeout;





}
