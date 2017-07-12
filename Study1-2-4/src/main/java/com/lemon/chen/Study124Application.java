package com.lemon.chen;

import com.lemon.chen.util.datasource.DynamicDataSourceRegister;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@MapperScan("com.lemon.chen.dao.mapper")//增加注解类，把mybatis注入
@Import({DynamicDataSourceRegister.class})  //进行多数据源注入
@ServletComponentScan("com.lemon.chen.monitor")//扫描servlet配置
public class Study124Application {

	public static void main(String[] args) {
		SpringApplication.run(Study124Application.class, args);
	}
}
