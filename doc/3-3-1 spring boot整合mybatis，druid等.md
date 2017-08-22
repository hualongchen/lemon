# 3-2-1 spring boot整合mybatis，druid等

### 1.0 引入mybatis， mapper,druid等maven Jar包
```
<!--基于数据库相关jar-->

        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>5.1.21</version>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-jdbc</artifactId>
        </dependency>

        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid-spring-boot-starter</artifactId>
            <version>1.1.1</version>
        </dependency>

        <!--mybatis-->
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
            <version>1.1.1</version>
        </dependency>

        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis</artifactId>
            <version>3.4.4</version>
        </dependency>
        <dependency>
            <groupId>tk.mybatis</groupId>
            <artifactId>mapper</artifactId>
            <version>3.4.0</version>
        </dependency>
        <dependency>
            <groupId>com.github.pagehelper</groupId>
            <artifactId>pagehelper</artifactId>
            <version>4.2.1</version>
        </dependency>

        <!-- 生成数据库代码 -->
        <dependency>
            <groupId>org.mybatis.generator</groupId>
            <artifactId>mybatis-generator-core</artifactId>
            <version>1.3.2</version>
        </dependency>
```


### 2.0 配置application.proterties文件相关
```
JDBC配置
spring.datasource.druid.one.url=jdbc:mysql://localhost:3306/test?characterEncoding=UTF-8
spring.datasource.druid.one.username=root
spring.datasource.druid.one.password=root
spring.datasource.druid.one.driver-class-name=com.mysql.jdbc.Driver

多数据源配置
spring.datasource.druid.two.url=jdbc:mysql://localhost:3306/study-user?characterEncoding=UTF-8
spring.datasource.druid.two.username=root
spring.datasource.druid.two.password=root
spring.datasource.druid.two.driver-class-name=com.mysql.jdbc.Driver

多数据源
spring.datasource.druid.three.url=jdbc:mysql://localhost:3306/study-point?characterEncoding=UTF-8
spring.datasource.druid.three.username=root
spring.datasource.druid.three.password=root
spring.datasource.druid.three.driver-class-name=com.mysql.jdbc.Driver
```
### 3.0 开始Druid数据源的配置详解(通过代码进行注解掉了)
```
# 连接池配置，说明请参考Druid Wiki，DruidDataSource配置属性列表
#spring.datasource.druid.initial-size=5
#spring.datasource.druid.max-active=20
#spring.datasource.druid.min-idle=5
#spring.datasource.druid.max-wait=60000
#spring.datasource.druid.pool-prepared-statements=true
#spring.datasource.druid.max-pool-prepared-statement-per-connection-size=20
#spring.datasource.druid.max-open-prepared-statements=5
#spring.datasource.druid.validation-query=SELECT 1 FROM DUAL
#spring.datasource.druid.validation-query-timeout=1
#spring.datasource.druid.test-on-borrow=false
#spring.datasource.druid.test-on-return=false
#spring.datasource.druid.test-while-idle=false
#spring.datasource.druid.time-between-eviction-runs-millis=300000
#spring.datasource.druid.min-evictable-idle-time-millis=300000
#spring.datasource.druid.max-evictable-idle-time-millis=300000
#spring.datasource.druid.filters=stat

#这些配置都可以用bean代码解决

# WebStatFilter配置，说明请参考Druid Wiki，配置_配置WebStatFilter
#spring.datasource.druid.web-stat-filter.enabled=true
#spring.datasource.druid.web-stat-filter.urlPattern=/
#spring.datasource.druid.web-stat-filter.exclusions=*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/dataSource/*
#spring.datasource.druid.web-stat-filter.sessionStatMaxCount=1000
#spring.datasource.druid.web-stat-filter.sessionStatEnable=true
#spring.datasource.druid.web-stat-filter.principalSessionName=
#spring.datasource.druid.web-stat-filter.principalCookieName=
#spring.datasource.druid.web-stat-filter.profileEnable=true

# StatViewServlet配置，说明请参考Druid Wiki，配置_StatViewServlet配置
#spring.datasource.druid.stat-view-servlet.enabled=true
#spring.datasource.druid.stat-view-servlet.urlPattern=/druid/*
#spring.datasource.druid.stat-view-servlet.resetEnable=true
#spring.datasource.druid.stat-view-servlet.loginUsername=lemon
#spring.datasource.druid.stat-view-servlet.loginPassword=lemon
#spring.datasource.druid.stat-view-servlet.allow=127.0.0.1
#spring.datasource.druid.stat-view-servlet.deny=127.0.0.2

# Spring监控配置，说明请参考Druid Github Wiki，配置_Druid和Spring关联监控配置
#spring.datasource.druid.aop-patterns=com.lemon.chen.service.*
#如果spring.datasource.druid.aop-patterns要代理的类没有定义interface请设置spring.aop.proxy-target-class=true
```
### 4.0 开始代码配置多数据源


#### 4.1 开始抽取公共方法——CommnDataSourceUtil
```
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
```

#### 4.2 进行数据源的注入

```
package com.lemon.chen.config;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * Created by chenhualong on 2017/7/21.
 */
@Configuration
// 扫描 Mapper 接口并容器管理
@MapperScan(basePackages = DruidPointDataSourceConfig.PACKAGE, sqlSessionFactoryRef = "pointSqlSessionFactory")
public class DruidPointDataSourceConfig {


    //master dao 所在的包
    public static final String PACKAGE = "com.lemon.chen.dao.mapper.point";

    //mapper所在目录
    private static final String MAPPER_LOCATION = "classpath:mapper/point/*.xml";


    @Value("${spring.datasource.druid.three.driver-class-name}")
    private String driverClassName;

    @Value("${spring.datasource.druid.three.url}")
    private String url;

    @Value("${spring.datasource.druid.three.username}")
    private String username;

    @Value("${spring.datasource.druid.three.password}")
    private String password;

    //初始化数据库连接
    @Bean(name = "pointDataSource")
    public DataSource masterDataSource() {

        return CommnDataSourceUtil.commonDataSource(driverClassName, url, username, password);
    }

    //数据源事务管理器
    @Bean(name = "pointDataSourceTransactionManager")
    public DataSourceTransactionManager masterDataSourceTransactionManager() {
        return new DataSourceTransactionManager(masterDataSource());
    }

    //创建Session
    @Bean(name = "pointSqlSessionFactory")
    public SqlSessionFactory masterSqlSessionFactory(@Qualifier("pointDataSource") DataSource masterDataSource) throws Exception {
        final SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(masterDataSource);
        //MapperLocations(Resource[] mapperLocations)
         sqlSessionFactoryBean.setPlugins(new Interceptor[]{CommnDataSourceUtil.setpageSource()});
        Resource[] mapperLocations = new PathMatchingResourcePatternResolver().getResources(DruidPointDataSourceConfig.MAPPER_LOCATION);
        sqlSessionFactoryBean.setMapperLocations(mapperLocations);
        return sqlSessionFactoryBean.getObject();
    }

}

```
#### 4.3  注入通用的mapper

```
package com.lemon.chen.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tk.mybatis.spring.mapper.MapperScannerConfigurer;

/**
 * Created by chenhualong on 2017/7/21.
 *
 * 因为必须要使用tk.mybatis.spring.mapper.MapperScannerConfigurer 注入才能使用通用mapper.
 *
 * 所以重新注入configuer才能使用,这个类建议放到config里面比较合适。
 *
 */

@Configuration
public class MapperScan {


    /**
     * 重新注入通用mapper的数据资源
     * @return
     */
    @Bean
    public MapperScannerConfigurer getMapper() {

        MapperScannerConfigurer configurer = new MapperScannerConfigurer();

        configurer.setBasePackage(DruidPointDataSourceConfig.PACKAGE);

        configurer.setSqlSessionFactoryBeanName("pointSqlSessionFactory");

        return configurer;

    }

}

```

### 5.0 增加druid的监控
```
package com.lemon.chen.monitor;

import com.alibaba.druid.support.http.WebStatFilter;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

/**
 * 配置过滤器,需要拦截哪些url,忽略哪些url,初始化参数等
 *
 * @date 17-4-7
 */
@WebFilter(filterName = "druidStatFilter",//过滤器名称
        urlPatterns = "/",//需要拦截的url
        initParams = {//filter初始化信息
                //需要忽略的资源
                @WebInitParam(name = "exclusions", value = "*.js,*.gif,*.jpg," +
                        "*.bmp,*.png,*.css,*.ico,/dataSource/*"),
                @WebInitParam(name = "sessionStatEnable", value = "true"),
                @WebInitParam(name = "profileEnable", value = "true")})
public class DruidStatFilter extends WebStatFilter {
}

```

### 5.1 增加druid状态拦截器

```
package com.lemon.chen.monitor;

import com.alibaba.druid.support.http.StatViewServlet;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

/**
 * 配置一个servlet,让我们可以访问监控页面
 *
 * @date 17-4-7
 */
//表明这是一个servlet
@WebServlet(urlPatterns = "/druid/*",//通过哪个url访问
        initParams = {
                @WebInitParam(name = "loginUsername", value = "lemon"),//用户名
                @WebInitParam(name = "loginPassword", value = "chen"), //密码
                @WebInitParam(name = "resetEnable", value = "true"),//是否可以重置
                @WebInitParam(name = "allow", value = "127.0.0.1"),//白名单
                @WebInitParam(name = "deny", value = "192.168.1.1")//黑名单
        })
public class DruidStatViewServlet extends StatViewServlet {
}

```

#### 5.3 自定义拦截器统计

```
package com.lemon.chen.monitor;

import com.alibaba.druid.support.spring.stat.DruidStatInterceptor;
import org.springframework.aop.Advisor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.JdkRegexpMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置Spring监控
 * @author chenhualong
 * @date 17-4-8
 */
@Configuration
public class MyDruidStatInterceptor {

    private static final String[] patterns = new String[]{"com.lemon.chen.service.*"};

    @Bean
    public DruidStatInterceptor druidStatInterceptor() {
        return new DruidStatInterceptor();
    }

    /**
     * 切入点
     * @return
     */
    @Bean
    public JdkRegexpMethodPointcut druidStatPointcut() {
        JdkRegexpMethodPointcut druidStatPointcut = new JdkRegexpMethodPointcut();
        druidStatPointcut.setPatterns(patterns);
        return druidStatPointcut;
    }

    /**
     * 配置aop
     * @return
     */
    @Bean
    public Advisor druidStatAdvisor() {
        return new DefaultPointcutAdvisor(druidStatPointcut(), druidStatInterceptor());
    }
}

``` 


#### 6.1  pom文件的插件配置

```
 <!--mybatis生成代码-->
            <plugin>
                <groupId>org.mybatis.generator</groupId>
                <artifactId>mybatis-generator-maven-plugin</artifactId>
                <version>1.3.2</version>
                <configuration>
                    <configurationFile>src/main/resources/MapperGeneratorConfig.xml</configurationFile>
                    <overwrite>true</overwrite>
                    <verbose>true</verbose>
                </configuration>
                <dependencies>
                    <dependency>
                        <groupId>mysql</groupId>
                        <artifactId>mysql-connector-java</artifactId>
                        <version>5.1.21</version>
                    </dependency>
                    <dependency>
                        <groupId>tk.mybatis</groupId>
                        <artifactId>mapper</artifactId>
                        <version>3.3.9</version>
                    </dependency>
                </dependencies>
            </plugin>

```