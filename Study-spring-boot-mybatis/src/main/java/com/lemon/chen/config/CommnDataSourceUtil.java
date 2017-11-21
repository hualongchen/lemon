package com.lemon.chen.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.pagehelper.PageHelper;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by chenhualong on 2017/7/21.
 */


public class CommnDataSourceUtil {


    /**
     * 配置分页插件，详情请查阅官方文档
     *
     * @return
     */
    public static PageHelper setpageSource() {


        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("pageSizeZero", "true");//分页尺寸为0时查询所有纪录不再执行分页
        properties.setProperty("reasonable", "true");//页码<=0 查询第一页，页码>=总页数查询最后一页
        properties.setProperty("supportMethodsArguments", "true");//支持通过 Mapper 接口参数来传递分页参数
        pageHelper.setProperties(properties);

        return pageHelper;
    }


    /**
     * 封装公共的datasource元素
     *
     * @param driverClassName
     * @param url
     * @param username
     * @param password
     * @return
     */
    public static DataSource commonDataSource(String driverClassName, String url, String username, String password) {

        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        dataSource.setInitialSize(5);
        dataSource.setMaxActive(20);
        dataSource.setMinIdle(5);
        dataSource.setMaxWait(60000);
        dataSource.setPoolPreparedStatements(true);
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(20);
        dataSource.setMaxOpenPreparedStatements(5);
        dataSource.setValidationQuery("SELECT 1 FROM DUAL");
        dataSource.setValidationQueryTimeout(1);
        dataSource.setTestOnBorrow(false);
        dataSource.setTestOnReturn(false);
        dataSource.setTestWhileIdle(false);
        dataSource.setMinEvictableIdleTimeMillis(100000);
        dataSource.setTimeBetweenEvictionRunsMillis(300000);
        dataSource.setMaxEvictableIdleTimeMillis(500000);

        try {
            dataSource.setFilters("stat");
        } catch (Exception ex) {
            System.out.println("init druid pool error");
        }
        return dataSource;

    }
}
