package com.lemon.chen.service;

import com.lemon.chen.api.UserService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * Created by chenhualong on 2017/7/17.
 */
@Service
public class UserServiceImpl2 implements UserService {


    private Logger logger = Logger.getLogger(getClass());

    /**
     * 一个接口多个实现
     *
     * @return
     */
    @Override
    public int countUser() {

        logger.info("this is UserServiceImpl2 countUser method ");

        System.out.println("this is UserServiceImpl2 countUser method ");
        return 200;
    }
}
