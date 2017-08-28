package com.tr.file.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.pagehelper.PageHelper;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by chenhualong on 2017/7/21.
 *
 * 抽取公共的方法进行复用
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
        /**
         * 加载mysql的驱动
         */
        dataSource.setDriverClassName(driverClassName);
        /**
         * 加载数据库连接
         */
        dataSource.setUrl(url);
        /**
         * 加载用户名
         */
        dataSource.setUsername(username);
        /**
         * 加载密码
         */
        dataSource.setPassword(password);

        /**
         * 初始化是个连接
         */
        dataSource.setInitialSize(10);
        /**
         * 最大的空闲连接
         */
        dataSource.setMaxActive(20);
        /**
         * 最小的空闲连接
         */
        dataSource.setMinIdle(5);
        /**
         * 获取连接时最大等待时间，单位毫秒。配置了maxWait之后，
         缺省启用公平锁，并发效率会有所下降，
         如果需要可以通过配置useUnfairLock属性为true使用非公平锁
         */
        dataSource.setMaxWait(60000);
        /**
         * 是否缓存preparedStatement，也就是PSCache。
         PSCache对支持游标的数据库性能提升巨大，比如说oracle。
         在mysql5.5以下的版本中没有PSCache功能，建议关闭掉
         */
        dataSource.setPoolPreparedStatements(false);
        /**
         *打开PSCache，并且指定每个连接上PSCache的大小,如果上面是false，下面就不用设置了
         */
        //dataSource.setMaxPoolPreparedStatementPerConnectionSize(20);

        /**
         * 要启用PSCache，必须配置大于0，当大于0时，poolPreparedStatements自动触发修改为true。
         * 在Druid中，不会存在Oracle下PSCache占用内存过多的问题，可以把这个数值配置大一些，比如说100
         */
       // dataSource.setMaxOpenPreparedStatements(5);
        /**
         * 验证使用的sql
         */
        dataSource.setValidationQuery("SELECT 1 FROM DUAL");

        /**
         * 验证超时时间
         */
        dataSource.setValidationQueryTimeout(10);
        /**
         * 借出连接时不要测试，否则很影响性能
         */
        dataSource.setTestOnBorrow(true);
        /**
         * 归还连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能
         */
        dataSource.setTestOnReturn(false);
        /**
         * 指明连接是否被空闲连接回收器(如果有)进行检验.如果检测失败,则连接将被从池中去除.
         * 建议配置为true，不影响性能，并且保证安全性。申请连接的时候检测，
         * 如果空闲时间大于timeBetweenEvictionRunsMillis，执行validationQuery检测连接是否有效
         */
        dataSource.setTestWhileIdle(true);
        /**
         * 每30秒运行一次空闲连接回收器
         */
        dataSource.setTimeBetweenEvictionRunsMillis(30000);
        /**
         *池中的连接空闲30分钟后被回收
         */
        dataSource.setMinEvictableIdleTimeMillis(1800000);


        /**
         * 最长连接回收时间，默认是七个小时，我们设置短点1个小时
         */
        dataSource.setMaxEvictableIdleTimeMillis(3600000);

        try {
            dataSource.setFilters("stat");
        } catch (Exception ex) {
            System.out.println("init druid pool error");
        }
        return dataSource;

    }
}
