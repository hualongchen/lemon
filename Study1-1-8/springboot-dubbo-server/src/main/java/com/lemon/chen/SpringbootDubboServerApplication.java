package com.lemon.chen;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;

import java.util.concurrent.CountDownLatch;

@SpringBootApplication
@ImportResource({"classpath:spring-dubbo-provider.xml"})
public class SpringbootDubboServerApplication {

	public static Logger  logger = LoggerFactory.getLogger(SpringbootDubboServerApplication.class);


	/**
	 * 线程计数器
	 * @return
	 */
	@Bean
	public CountDownLatch closeLatch() {
		return new CountDownLatch(1);
	}


	public static void main(String[] args)throws InterruptedException {
		ApplicationContext ctx =SpringApplication.run(SpringbootDubboServerApplication.class, args);
		logger.info("项目启动!");
		CountDownLatch closeLatch = ctx.getBean(CountDownLatch.class);
		closeLatch.await();
	}
}
