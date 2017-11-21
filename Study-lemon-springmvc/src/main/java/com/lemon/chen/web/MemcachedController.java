package com.lemon.chen.web;

import com.lemon.chen.lemon.memcache.MemcachedUtil;
import com.lemon.chen.lemon.tools.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by chenhualong on 16/7/31.
 */

//@Controller
//@RequestMapping(value = "/mc")
public class MemcachedController {


    @Autowired
    private MemcachedUtil  memcachedUtil ;



    @RequestMapping(value = "add")
    @ResponseBody
    public Object findMc(){

        ResultVO<String> vo = new ResultVO<String>();


        vo.setErrorCode("200");
        vo.setIsSuccess(true);
        vo.setData("我很好奇");


        ResultVO<String> vo2 = new ResultVO<String>();


        vo2.setErrorCode("200");
        vo2.setIsSuccess(true);
        vo2.setData("我很好奇2");


        memcachedUtil.setDataToMemcache(180, "firstKey", vo);

        memcachedUtil.setDataToMemcache(180, "firstKey2", vo2);


        vo=   memcachedUtil.getDataFromMemcache("firstKey");

        if(null!=vo){
            System.out.println(vo.getData().toString());
        }else{

            System.out.println("获取的数据为空");
        }

        return "success";
    }

}
