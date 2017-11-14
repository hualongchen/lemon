package com.lemon.log4j.login;

import org.apache.log4j.Logger;

/**
 * @author :chenhualong
 * @Date : created in 17:12 2017/11/14
 * @Description :
 */
public class TestLoginLog4j {


    private static final Logger file = Logger.getLogger("file");
    private static final Logger register = Logger.getLogger("register");
    private static final Logger login = Logger.getLogger("login");
    private static final Logger jjj = Logger.getLogger(TestLoginLog4j.class.getName());
    private static org.apache.log4j.Logger log = Logger.getLogger(TestLoginLog4j.class);

    public static void main(String[] args) {



        file.info("file info");
        file.warn("file warn");
        file.error("file error");


        register.error("register error");
        register.info("register info");
        register.warn("register warn");


        login.warn("login warn");
        login.error("login error");
        login.info("login info");


        jjj.info("JJJ info");
        jjj.error("JJJ error");
        jjj.warn("JJJ warn");

        log.info("log info");
        log.error("log error");
        log.warn("log warn");
    }
}
