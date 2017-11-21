package com.lemon.springboot.study125.web;


import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author chenhualong
 * 进行页面路由展示
 */

@Controller
public class PageController {


    /**
     *简单访问个首页
     */
    @RequestMapping(value ="/")
    public String index(ModelMap map){

        map.addAttribute("title","亲，那你简直帅爆了");

        return "index";
    }



}
