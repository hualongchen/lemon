package com.lemon.springboot.study125.dao.mapper.user;

import com.lemon.springboot.study125.dao.bean.user.SpringBootUserPO;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SpringBootUserPOMapper extends Mapper<SpringBootUserPO> {


    @Select("select id,username,create_time,update_time from study_springboot_user where id=#{id}")
     List<SpringBootUserPO>  findbyUserName(SpringBootUserPO  springBootUserPO);


    @Select("select id,username,create_time,update_time from study_springboot_user where id=#{id}")
    SpringBootUserPO  findbyUserName4(int id );


    @Select("select id,username,create_time,update_time from study_springboot_user where username=#{username} order by create_time desc")
     List<SpringBootUserPO>  findbyUserName2(SpringBootUserPO  springBootUserPO);


    @Select("select id,username,create_time,update_time from study_springboot_user where username=#{username} order by create_time asc")
    List<SpringBootUserPO>  findbyUserName3(SpringBootUserPO  springBootUserPO);



}