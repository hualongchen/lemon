package com.lemon.springboot.study125.service.impl;

import com.lemon.springboot.study125.dao.bean.user.SpringBootUserPO;
import com.lemon.springboot.study125.dao.mapper.user.SpringBootUserPOMapper;
import com.lemon.springboot.study125.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author chenhualong
 */
@Service
public class UserServiceImpl implements UserService {



    @Autowired
    private SpringBootUserPOMapper springBootUserPOMapper;


    /**
     * 增加测试用户
     * @param springBootUserPO  用户属性
     * @return  返回增加属性
     */
    @Override
    public SpringBootUserPO addUser(SpringBootUserPO springBootUserPO) {

        springBootUserPO.setUsername("通融");

        springBootUserPOMapper.insert(springBootUserPO);

        return springBootUserPOMapper.selectAll().get(0);
    }
}
