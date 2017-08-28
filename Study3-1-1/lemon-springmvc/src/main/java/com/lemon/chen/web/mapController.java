package com.lemon.chen.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by chenhualong on 16/7/16.
 */

@Controller
public class mapController {


    /**
     * 跳转到百度生成页
     * @param request
     * @return
     */
    @RequestMapping(value = "/map")
    public String returnToMap(HttpServletRequest request){


        return "map";

    }


    /**
     * 测试跨域早餐呢过method变更
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/domain" ,method = RequestMethod.POST,produces = { "application/json;charset=UTF-8" })
    public Object  testDomain(HttpServletRequest request,HttpServletResponse response){

        response.addHeader("Access-Control-Allow-Origin", "*");

        String test=request.getParameter("test");

        System.out.println(test);

        return "success";
    }

}
