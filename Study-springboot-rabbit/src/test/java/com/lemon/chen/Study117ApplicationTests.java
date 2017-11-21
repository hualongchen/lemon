package com.lemon.chen;

import com.lemon.chen.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Study117ApplicationTests {


	@Autowired
	private UserService userService;

	@Test
	public void contextLoads() {

		userService.sendMessage();
	}

}
