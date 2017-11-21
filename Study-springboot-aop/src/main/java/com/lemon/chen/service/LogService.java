package com.lemon.chen.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by chenhualong on 2017/7/6.
 */

@Service
public class LogService {


    private Logger  logger = LoggerFactory.getLogger(LogService.class);


    public void testLog(){



        logger.info("this is LogService log method info log");

        logger.error("this is LogService log method error log");

        logger.warn("this is LogService log method warn log");

        logger.trace("this is LogService log method trace log");


    }
}
