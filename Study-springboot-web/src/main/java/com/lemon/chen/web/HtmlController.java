package com.lemon.chen.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by chenhualong on 2017/7/6.
 */

@Controller
public class HtmlController {


    @RequestMapping(value ="/")
    public String index(ModelMap map){

        map.addAttribute("title","亲，那你简直帅爆了");

        return "index";
    }
}
