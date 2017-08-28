package com.lemon.chen.web;

import com.lemon.chen.biz.FirstBizServer;
import com.lemon.chen.content.ResultVO;
import com.lemon.chen.content.testForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by chenhualong on 16/5/4.
 */

@Controller
public class FirstWebController {



    @Autowired
    private FirstBizServer bizServer ;


    private static final Logger logger = LoggerFactory.getLogger(FirstWebController.class);

    /**
     * 第一个web 方法
     */
    @RequestMapping(value = "firstName")
    @ResponseBody
    public  Object  testFirstWebAction(){


        System.out.println("this is  my fist mac maven project !!!");

        System.out.println(bizServer.firstBizserverService());

        return bizServer.firstBizserverService();
    }


    /**
     * 测试返回页
     * @param
     * @return
     */
    @RequestMapping(value = "/",method = RequestMethod.GET)
    @ResponseBody
    public Object testSecondWebAction(){


        System.out.println("this is testSecondWebAction fucntion");
        logger.info("this is info  log");
        logger.debug("this is debug log");
        logger.error("this is error log");
        HashMap<String,String>  map  = new HashMap<String, String>();

        map.put("chen", "hualong");


        return map;
    }


    @RequestMapping(value = "page")
    public String  testThirdWebAction(HttpServletRequest request){


        return "index";
    }


    @RequestMapping(value = "page2")
    public String  testThirdWebAction2(HttpServletRequest request){


        return "two";
    }




    @RequestMapping(value = "update")
    @ResponseBody
    public Object  testUpdateDatabase(HttpServletRequest request, @ModelAttribute testForm form){


        bizServer.secondServerService(form);

        return  new ResultVO();
    }

    
}

