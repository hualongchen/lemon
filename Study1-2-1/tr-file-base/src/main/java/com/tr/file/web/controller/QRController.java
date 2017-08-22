package com.tr.file.web.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class QRController {




    @GetMapping("/good")
    public Object  createQR(HttpServletRequest request){

        String agreementId = request.getParameter("agreementId");

        if(null!= agreementId){
            System.out.println("=====合同号:"+ agreementId);
        }else{

            System.out.println("没有得到任何的资源");
        }

        return agreementId ;
    }



}
