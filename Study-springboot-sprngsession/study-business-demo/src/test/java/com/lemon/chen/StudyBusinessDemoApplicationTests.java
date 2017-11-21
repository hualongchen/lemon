package com.lemon.chen;

import com.lemon.chen.dao.bean.user.TrUserInfoPO;
import com.lemon.chen.dao.mapper.user.TrUserInfoPOMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StudyBusinessDemoApplicationTests {


	@Autowired
	private TrUserInfoPOMapper userInfoPOMapper ;
	@Test
	public void contextLoads() {

		/**
		 * 测试代码
		 * @param userInfoPO
		 * @return
		 */

		TrUserInfoPO  userInfoPO = new TrUserInfoPO();


			userInfoPO.setCreatetime(new Date());
			userInfoPO.setImel("1234567890");
			userInfoPO.setMobilephone("13883338765");
			userInfoPO.setNickname("小丑");
			userInfoPO.setPassword("root");
			userInfoPO.setUserid("20170816");
			userInfoPO.setUsername("lemon");

			int k = userInfoPOMapper.insertSelective(userInfoPO);



	}

}
