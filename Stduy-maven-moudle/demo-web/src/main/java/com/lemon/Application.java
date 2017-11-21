package com.lemon;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.lemon.common.filter.XssStringJsonSerializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;


/**
 * @author chenhualong
 */
@SpringBootApplication
@ServletComponentScan
public class Application {



	@Bean
	@Primary
	public ObjectMapper xssObjectMapper(Jackson2ObjectMapperBuilder builder) {
		//解析器
		ObjectMapper objectMapper = builder.createXmlMapper(false).build();
		//注册xss解析器
		SimpleModule xssModule = new SimpleModule("XssStringJsonSerializer");
		xssModule.addSerializer(new XssStringJsonSerializer());
		objectMapper.registerModule(xssModule);
		//返回
		return objectMapper;
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
