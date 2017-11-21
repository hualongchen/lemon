package com.lemon.chen.web;

import com.lemon.chen.service.LogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by chenhualong on 2017/7/6.
 */

@RestController
public class LogController {


    private Logger logger = LoggerFactory.getLogger(LogController.class);


    @Autowired
    private LogService service;


    @GetMapping("/log")
    public String log() {

        logger.info("this is logController log method info log");

        logger.error("this is logController log method error log");

        logger.warn("this is logController log method warn log");

        logger.trace("this is logController log method trace log");

        logger.debug("this is logController log method debug log");

        service.testLog();

        return "success";
    }


}
