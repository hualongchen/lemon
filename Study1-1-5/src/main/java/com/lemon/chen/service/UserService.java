package com.lemon.chen.service;

import com.lemon.chen.domain.User;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * Created by chenhualong on 2017/7/10.
 */

@Service
@CacheConfig   //此注解是这个类统一需要的一些缓存放在里面
public class UserService {


    /** 将对应id为key的值，换船呢到User这个缓存对象中， 此User不是实体类的User.
     * 返回随机数
     * @return
     */
    @Cacheable(value = "user",key = "#id")
     public User randomInt(int id){

         User  user = new User();

        user.setUserId(Math.random());

        return user ;
     }


    /**
     * 此注解为删除方法，并删除缓存里面的值
     * @param id
     */
     @CacheEvict(value = "user",key = "#id")
     public void delrandomInt(int id){

         System.out.println("删除 id为："+id+"的 User");

     }

    /**
     * 和 cacheable用法相同，主要用于新增和更新
     * @param id
     * @return
     */
     @CachePut(value = "#id")
     public User addUser(int id){

         User  user = new User();

         user.setUserId(Math.random());

         return user ;
     }


    /**
     * 此注解可以定义有条件的判断缓存   condition
     * @param id
     * @return
     */
    @Cacheable(key = "#id", condition = "#id.length() < 3")
     public User randomInt2(int id){

         User  user = new User();

         user.setUserId(Math.random());

         return user ;
     }



    public static void main(String[] args) {

        System.out.println(Math.random());
    }
}
