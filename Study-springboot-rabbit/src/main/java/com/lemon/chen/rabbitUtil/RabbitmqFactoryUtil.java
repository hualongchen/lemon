package com.lemon.chen.rabbitUtil;

import com.rabbitmq.client.*;
import com.sun.deploy.util.StringUtils;

import java.io.IOException;
import java.lang.String;
import java.lang.System;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

/**
 * Created by chenhualong on 2017/8/7.
 */
public class RabbitmqFactoryUtil {


    /**
     * 提供rabbitmq连接
     *
     * @return
     * @throws Throwable
     */
    public static Connection getConnection() throws Throwable {


        ConnectionFactory facotry = new ConnectionFactory();
        facotry.setUsername("root");
        facotry.setPassword("root");
        facotry.setVirtualHost("/");
        facotry.setHost("10.55.120.247");

        Connection conn = facotry.newConnection(); //获取一个链接

        return conn;

    }

    /**
     * 关闭连接
     * @param conn
     * @param channel
     */
    public static void closeConnection(Connection conn, Channel channel) {

        try {

            channel.close();

            conn.close();
        } catch (Exception ex) {

            channel = null;

            conn = null;
        }
    }
}
