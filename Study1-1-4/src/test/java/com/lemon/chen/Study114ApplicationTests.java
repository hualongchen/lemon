package com.lemon.chen;

import com.lemon.chen.service.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Study114ApplicationTests {


    @Autowired
    private UserServiceImpl userService;


    @Test
    public void test() {


        userService.deleteAllUsers();

        userService.create("lemon1", 12);

    }

    /**
     * 测试事务,测试成功，数据表刘
     */
    @Test
    public void test2(){

        userService.deleteAllUsersFroTransaction();
    }


}
