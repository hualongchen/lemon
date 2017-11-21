package com.tr.file.util.Lombok;


import lombok.extern.slf4j.Slf4j;

/**
 * log 有六种选的方式
 *
 * @CommonsLog Creates log = org.apache.commons.logging.LogFactory.getLog(LogExample.class);
 * @Log Creates log = java.util.logging.Logger.getLogger(LogExample.class.getName());
 * @Log4j Creates log = org.apache.log4j.Logger.getLogger(LogExample.class);
 * @Log4j2 Creates log = org.apache.logging.log4j.LogManager.getLogger(LogExample.class);
 * @Slf4j Creates log = org.slf4j.LoggerFactory.getLogger(LogExample.class);
 * @XSlf4j Creates log = org.slf4j.ext.XLoggerFactory.getXLogger(LogExample.class);
 */
@Slf4j
public class LombokLogTest {


    public static void main(String[] args) {

        log.info("yes ,it is very easy");

    }
}
