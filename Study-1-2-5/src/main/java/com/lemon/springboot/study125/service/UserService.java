package com.lemon.springboot.study125.service;

import com.lemon.springboot.study125.dao.bean.user.SpringBootUserPO;

/**
 * @author chenhualong
 */
public interface UserService {

    /**
     * 增加并查询用户
     * @param springBootUserPO  用户属性
     * @return 用户信息
     */
    public SpringBootUserPO  addUser(SpringBootUserPO  springBootUserPO);



}
