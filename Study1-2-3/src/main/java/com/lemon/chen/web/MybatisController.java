package com.lemon.chen.web;

import com.github.pagehelper.PageHelper;
import com.lemon.chen.dao.mapper.mbuser.MbUserPOMapper;
import com.lemon.chen.dao.mapper.point.PointInfoPOMapper;
import com.lemon.chen.dao.mapper.shopuser.UserInfoPOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by chenhualong on 2017/7/20.
 */

@RestController
public class MybatisController {


    @Autowired
    private MbUserPOMapper  mbUserPOMapper ;

    @Autowired
    private UserInfoPOMapper userInfoPOMapper ;

    @Autowired
    private PointInfoPOMapper   pointInfoPOMapper ;


    @RequestMapping("/shop")
    private Object  shopUserTest(){


        return userInfoPOMapper.selectByPrimaryKey(3L);
    }

    @RequestMapping("mb")
    private Object  mbUserTest(){

        return mbUserPOMapper.selectByPrimaryKey(1) ;
    }


    @RequestMapping("/point")
    private Object pointUserTest(){

        return pointInfoPOMapper.selectAll().get(0);
    }


    @RequestMapping("limt")
    public Object  pointlimit(){


        //第一个参数是页数， 第二个参数是显示个数
        PageHelper.startPage(1,3);

        return pointInfoPOMapper.selectAll();
    }
}
