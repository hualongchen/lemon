package com.lemon.chen.service;

import com.lemon.chen.api.UserService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * Created by chenhualong on 2017/7/17.
 */

@Service
public class UserServiceImpl implements UserService {


    private Logger logger = Logger.getLogger(getClass());

    /**
     * 开始dubbo的查库之类的原子操作
     * @return
     */
    @Override
    public int countUser() {

        logger.info("this is UserServiceImpl countUser method ");

        /**
         * 里面各种查库，验证之类的调用
         *
         * XXXXXXX
         * XXXXXXX
         * XXXXXXX
         */

        System.out.println("this is UserServiceImpl countUser method  ");
        return 100;
    }
}
