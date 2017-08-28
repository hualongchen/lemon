package com.lemon.chen.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by chenhualong on 16/6/21.
 */

@Controller
public class SecondController {




    @RequestMapping(value = "red" ,method = RequestMethod.GET,produces = { "text/html;charset=UTF-8" })
    public String test11(HttpServletRequest request , HttpServletResponse response){

        System.out.println("----->redirect----red2   redirect");
        return "redirect:/red2";
    }

    @RequestMapping(value = "red2" ,method = RequestMethod.GET,produces = { "text/html;charset=UTF-8" })
    public String test12(HttpServletRequest request , HttpServletResponse response){

        request.setAttribute("name", "fuck");

        System.out.println("------>this is red2  forward");
        return "forward:/red3";
    }



    @RequestMapping(value = "red3" ,method = RequestMethod.GET,produces = { "text/html;charset=UTF-8" })
    public String test13(HttpServletRequest request , HttpServletResponse response){

        System.out.println("------->this is red3");
        return "index";
    }

}
