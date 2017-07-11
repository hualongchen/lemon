package com.lemon.chen;

import com.lemon.chen.service.RedisService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Study116ApplicationTests {

	@Autowired
	private RedisService redisService ;


	/**
	 * 测试，先存后取
	 */
	@Test
	public void contextLoads() {

		redisService.addUser();
		System.out.println(redisService.getUserByName().toString());
	}

}
