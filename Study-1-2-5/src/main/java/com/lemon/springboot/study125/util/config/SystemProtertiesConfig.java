package com.lemon.springboot.study125.util.config;


import com.lemon.springboot.study125.modul.SystemProperties;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author chenhualong
 */
//@Configuration
public class SystemProtertiesConfig {




    /**
     * 获取外部自定义配置文件属性
     * @return  PropertiesConfigBean
     */
    @SneakyThrows
    @Bean
    public SystemProperties getPath() {

        SystemProperties propertiesConfigBean = new SystemProperties();

        Properties properties = new Properties();

        String path = System.getProperty("SEC_CONF");

        InputStream in = new FileInputStream(path + "/config.properties");


        properties.load(in);

        in.close();


        return propertiesConfigBean;

    }
}
