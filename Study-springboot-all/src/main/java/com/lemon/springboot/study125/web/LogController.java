package com.lemon.springboot.study125.web;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chenhualong
 * 进行日志的演示
 */

@Slf4j
@RestController
public class LogController {


    /**
     * 测试各种日志形式
     * @return
     */
    @GetMapping("/log")
    public String log() {

        log.info("this is logController log method info log");

        log.error("this is logController log method error log");

        log.warn("this is logController log method warn log");

        log.trace("this is logController log method trace log");

        log.debug("this is logController log method debug log");

        return "success";
    }

}
