package com.lemon.springboot.study125.web;

import com.lemon.springboot.study125.dao.bean.user.SpringBootUserPO;
import com.lemon.springboot.study125.dao.mapper.user.SpringBootUserPOMapper;
import com.lemon.springboot.study125.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * @author chenhualong
 */

@RestController
public class MybatisController {


    @Autowired
    private SpringBootUserPOMapper  springBootUserPOMapper;

    @Autowired
    private UserService  userService ;

    /**
     * 测试一  是否打通数据库
     * @return
     */
    @GetMapping("/mybatis")
    public Object test(){


        SpringBootUserPO  springBootUserPO = new SpringBootUserPO();

        springBootUserPO.setUsername("通融");

        springBootUserPOMapper.insert(springBootUserPO);


        return springBootUserPOMapper.selectAll().get(0);

    }


    @GetMapping("/mybatis6")
    public Object test22(){


        SpringBootUserPO  springBootUserPO = new SpringBootUserPO();

        springBootUserPO.setUsername("通融");

        Example  example = new Example(springBootUserPO.getClass());


       example.setOrderByClause("create_time DESC");  //设置排序


        Criteria criteria = example.createCriteria();

        if(null != springBootUserPO.getUsername()){

            criteria.andEqualTo("username",springBootUserPO.getUsername());
        }

       // criteria.andBetween("id",1,6);

       // criteria.andGreaterThan("id",5);

       // criteria.andLessThan("id",2);

       // criteria.andIsNotNull("username");

        //criteria.andCondition("group by id");

        return springBootUserPOMapper.selectByExample(example);

    }


    @GetMapping("/mybatis1")
    public Object  test1(){

        SpringBootUserPO  springBootUserPO = new SpringBootUserPO();

        springBootUserPO.setUsername("通融");

        springBootUserPO.setId(3);

        return  springBootUserPOMapper.findbyUserName(springBootUserPO);
    }


    @GetMapping("/mybatis2")
    public Object  test11(){

        SpringBootUserPO  springBootUserPO = new SpringBootUserPO();

        springBootUserPO.setUsername("通融");
        return  springBootUserPOMapper.findbyUserName2(springBootUserPO);
    }



    @GetMapping("/mybatis3")
    public Object  test111(){

        SpringBootUserPO  springBootUserPO = new SpringBootUserPO();

        springBootUserPO.setUsername("通融");
        return  springBootUserPOMapper.findbyUserName3(springBootUserPO);
    }


    @GetMapping("/mybatis4")
    public Object  test1111(){

        return  springBootUserPOMapper.findbyUserName4(1);
    }






    /**
     * 测试二，联通mybatis表加事务
     * @return
     */
    @GetMapping("/mybatis5")
    public Object test2(){

        SpringBootUserPO  springBootUserPO = new SpringBootUserPO();

        springBootUserPO.setUsername("通融2");

        return userService.addUser(springBootUserPO);
    }



}
