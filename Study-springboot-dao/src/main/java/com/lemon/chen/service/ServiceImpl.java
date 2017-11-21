package com.lemon.chen.service;

import com.lemon.chen.dao.bean.MbUserPOExample;
import com.lemon.chen.dao.mapper.MbUserPOMapper;
import com.lemon.chen.util.datasource.TargetDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by chenhualong on 2017/7/10.
 */

@Service
public class ServiceImpl {

    @Autowired
    private MbUserPOMapper  userPOMapper ;



    //@TargetDataSource(name = "dataSource")
    public void findUser(){

        MbUserPOExample  userPOExample = new MbUserPOExample();

        userPOExample.createCriteria().andUseridEqualTo(1);
        int k =userPOMapper.countByExample(userPOExample);

        System.out.println("userPOMapper.countByExample(userPOExample) is："+k);
    }



}
