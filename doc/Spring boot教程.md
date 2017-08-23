# Spring boot 入门教程

### 1 Spring boot 入门

#### 1.1 spring boot初始化的maven配置

```
<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>1.5.4.RELEASE</version>
		<relativePath/> <!-- lookup parent from repository -->
	</parent>


	<properties>
		<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
		<project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
		<java.version>1.8</java.version>
	</properties>

	<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
	</dependencies>

	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
		</plugins>
	</build>
```

#### 1.2 指定application.properties，常规的application的配置

```
server.port=9999
com.lemon.blog.value=${random.value}
com.lemon.blog.number=${random.int}
com.lemon.blog.bignumber=${random.long}
com.lemon.blog.test1=${random.int(10)}
com.lemon.blog.test2=${random.int[10,20]}
com.lemon.name=\u9648\u534E\u9F99
com.lemon.title=\u4E24\u6C5F\u65B0\u533A\u901A\u878D\u5C0F\u8D37\u516C\u53F8
com.lemon.company.desc=${com.lemon.name}\u52AA\u529B\u7684\u5728\u5199demo${com.lemon.title}\u7684\u8BA1\u5212\u65B9\u6848
spring.profiles.active=dev
```

#### 1.3 修改项目启动的banner

> 在resources下面新建一个banner.txt，在里面增加图案

```
////////////////////////////////////////////////////////////////////
//                          _ooOoo_                               //
//                         o8888888o                              //
//                         88" . "88                              //
//                         (| ^_^ |)                              //
//                         O\  =  /O                              //
//                      ____/`---'\____                           //
//                    .'  \\|     |//  `.                         //
//                   /  \\|||  :  |||//  \                        //
//                  /  _||||| -:- |||||-  \                       //
//                  |   | \\\  -  /// |   |                       //
//                  | \_|  ''\---/''  |   |                       //
//                  \  .-\__  `-`  ___/-. /                       //
//                ___`. .'  /--.--\  `. . ___                     //
//              ."" '<  `.___\_<|>_/___.'  >'"".                  //
//            | | :  `- \`.;`\ _ /`;.`/ - ` : | |                 //
//            \  \ `-.   \_ __\ /__ _/   .-` /  /                 //
//      ========`-.____`-.___\_____/___.-`____.-'========         //
//                           `=---='                              //
//      ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^        //
//            佛祖保佑       永不宕机     永无BUG                    //
////////////////////////////////////////////////////////////////////
```

#### 1.4 项目中获取application里面的参数值

```
@Component
public class ApplicationContents {



    @Value("${com.lemon.blog.value}")
    private String blogValue ;

    @Value("${com.lemon.blog.number}")
    private Integer blogNumber;

    @Value("${com.lemon.blog.bignumber}")
    private Long bigNumber ;

    @Value("${com.lemon.blog.test1}")
    private Integer test1;

    @Value("${com.lemon.blog.test2}")
    private Integer test2;

    @Value("${com.lemon.name}")
    private String  lemonName ;

    @Value("${com.lemon.title}")
    private String  title ;

    @Value("${com.lemon.company.desc}")
    private String desc ;

    @Value("${com.lemon.company.desc2}")
    private String desc2 ;

}

```

#### 1.5 第一个简单的controller

```
@RestController
public class HelloController {


    @Autowired
    private ApplicationContents applicationContents;


    @RequestMapping(value = "/hello", method = RequestMethod.GET,produces = { "application/json;charset=UTF-8" })
    public Object helloWorld() {

        return "hello world";
    }

}
```

#### 1.6 简单的单元测试(利用MVC进行测试)

```
package com.lemon.chen;

import com.lemon.chen.web.HelloController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class ApplicationTests {


    private MockMvc mvc;

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.standaloneSetup(new HelloController()).build();
    }


    @Test
    public void contextLoads() {
        System.out.println("first spring boot junit test");
    }


    /**
     * 利用mvc进行单元测试
     * @throws Throwable
     */
    @Test
    public void test1() throws Throwable {

        mvc.perform(MockMvcRequestBuilders.get("/hello").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("Hello World")));
    }



    @Test
    public void testUserController() throws Exception {
//  	测试UserController
        RequestBuilder request = null;

        // 1、get查一下user列表，应该为空
        request = get("/users/");
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("[]")));

        // 2、post提交一个user
        request = post("/users/")
                .param("id", "1")
                .param("name", "测试大师")
                .param("age", "20");
        mvc.perform(request)
//				.andDo(MockMvcResultHandlers.print())
                .andExpect(content().string(equalTo("success")));

        // 3、get获取user列表，应该有刚才插入的数据
        request = get("/users/");
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("[{\"id\":1,\"name\":\"测试大师\",\"age\":20}]")));

        // 4、put修改id为1的user
        request = put("/users/1")
                .param("name", "测试终极大师")
                .param("age", "30");
        mvc.perform(request)
                .andExpect(content().string(equalTo("success")));

        // 5、get一个id为1的user
        request = get("/users/1");
        mvc.perform(request)
                .andExpect(content().string(equalTo("{\"id\":1,\"name\":\"测试终极大师\",\"age\":30}")));

        // 6、del删除id为1的user
        request = delete("/users/1");
        mvc.perform(request)
                .andExpect(content().string(equalTo("success")));

        // 7、get查一下user列表，应该为空
        request = get("/users/");
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("[]")));

    }

}

```


#### 1.7 增加全局异常判断

#####1.7.1  增加自定义业务异常
```
package com.lemon.chen.util;

/**
 * Created by chenhualong on 2017/7/6.
 */

public class BusinessException extends Exception {

    public BusinessException(String message) {
        super(message);
    }
}

```

#####1.7.2 全局异常捕获

```
package com.lemon.chen.util;

import com.lemon.chen.bean.ErrorInfo;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by chenhualong on 2017/7/6.
 */
@ControllerAdvice
public class GlobalExceptionHandler {


    /**
     * 跳转到错误页面
     * @param req
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = Exception.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", e);
        mav.addObject("url", req.getRequestURL());
        mav.setViewName("error");

        return mav;
    }

    /**
     * 返回json数据
     * @param req
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = BusinessException.class)
    @ResponseBody
    public ErrorInfo<String> jsonErrorHandler(HttpServletRequest req, BusinessException e) throws Exception {
        ErrorInfo<String> r = new ErrorInfo<>();
        r.setMessage(e.getMessage());
        r.setCode(ErrorInfo.ERROR);
        r.setData("Some Data");
        r.setUrl(req.getRequestURL().toString());
        return r;
    }
}

```

#### 1.8 使用Swagger2 进行api调试定义

##### 1.8.1 增加Swagger2 的maven定义

```
<!--增加swagger API文档包-->
		<dependency>
			<groupId>io.springfox</groupId>
			<artifactId>springfox-swagger2</artifactId>
			<version>2.2.2</version>
		</dependency>
		<dependency>
			<groupId>io.springfox</groupId>
			<artifactId>springfox-swagger-ui</artifactId>
			<version>2.2.2</version>
		</dependency>

```

##### 1.8.2 增加Swagger2的工具定义类

```
package com.lemon.chen.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by chenhualong on 2017/7/6.
 */


@Configuration  //让spring识别并加入
@EnableSwagger2  //开启swagger2的功能
public class SwaggerUtil {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.lemon.chen"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Spring Boot中使用Swagger2构建RESTful APIs")
                .description("更多Spring Boot相关文章请关注：https://github.com/hualongchen/lemon")
                .termsOfServiceUrl("https://github.com/hualongchen/lemon")
                .contact("lemon")
                .version("1.0")
                .build();
    }


}

```

##### 1.8.3在web应用添加接口注释

```

 /**
     * @param usereId
     * @return
     * @RequestParam get查询方式    通过
     */
    @ApiOperation(value="根据用户ID查找用户信息", notes="")
    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public UserForm getUser(@RequestParam int usereId) {

        return userMap.get(usereId);
    }

    /**
     * @param id 通过
     * @return
     * @PathVariable 利用路径进行查询， get的方式
     */
    @ApiOperation(value="获取用户详细信息", notes="根据url的id来获取用户详细信息")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public UserForm getUser2(@PathVariable int id) {

        return userMap.get(id);
    }

    /**
     * 利用PathVariable，ModelAttribute put的方式进行更新
     *
     * @param id   通过
     * @param user
     * @return
     */
    @ApiOperation(value="存储用户详细信息", notes="根据url的id来指定存储对象，并根据传过来的user信息来更新用户详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "UserForm")
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public String putUser(@PathVariable Long id, @ModelAttribute UserForm user) {
        System.out.println(user.toString());
        System.out.println(id);
        return "success";
    }

```

##### 1.8.4  进行web UI进行访问和管理

>   http://localhost:8080/swagger-ui.html


#### 1.9 使用模板进行web开发

#####1.9.1 添加对应的maven

```
       <groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-web</artifactId>
   	</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-thymeleaf</artifactId>
		</dependency>

		<!--如果用freemarker则替换为此包-->
		<!--<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-freemarker</artifactId>
		</dependency>-->

		<!--如果用velocity则替换为此包-->
		<!--<dependency>-->
			<!--<groupId>org.springframework.boot</groupId>-->
			<!--<artifactId>spring-boot-starter-velocity</artifactId>-->
		<!--</dependency>-->

```

#####1.9.2 添加application配置文件属性

```
spring.thymeleaf.cache=true
spring.thymeleaf.check-template-location=true
spring.thymeleaf.content-type=text/html
spring.thymeleaf.enabled=true
spring.thymeleaf.encoding=UTF-8
spring.thymeleaf.excluded-view-names=
spring.thymeleaf.mode=HTML5
spring.thymeleaf.prefix=classpath:/templates/
spring.thymeleaf.suffix=.html

```

#####1.9.3 静态资源文件存放

1. JS，css， Img此类文件存放在resource下面的static文件夹中。

2. 存放前端模板的路径为，resources--->templates

3. 模板的样式为：

```
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8" />
    <title></title>
</head>
<body>
FreeMarker模板引擎
<h1>${title}</h1>
</body>
</html>
```

##### 1.9.4 web层进行访问

```
@Controller
public class HtmlController {


    @RequestMapping(value ="/")
    public String index(ModelMap map){

        map.addAttribute("title","亲，那你简直帅爆了");

        return "index";
    }
}

```
#### 2.1 spring boot使用AOP

##### 2.1.1 加入需要使用的maven

```
<!--面向切面编程-->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-aop</artifactId>
		</dependency>

```

#####2.1.2 AOP相关的注解解释
1. 使用@Aspect注解将一个java类定义为切面类
2. 使用@Pointcut定义一个切入点，可以是一个规则表达式，比如下例中某个package下的所有函数，也可以是一个注解等。
根据需要在切入点不同位置的切入内容
3. 使用@Before在切入点开始处切入内容
4. 使用@After在切入点结尾处切入内容
5. 使用@AfterReturning在切入点return内容之后切入内容（可以用来对处理返回值做一些加工处理）
6. 使用@Around在切入点前后切入内容，并自己控制何时执行切入点自身的内容
7. 使用@AfterThrowing用来处理当切入内容部分抛出异常之后的处理逻辑

#####2.1.3 AOP在application配置文件属性

```
spring.aop.auto=true
spring.aop.proxy-target-class=false
spring.output.ansi.enabled=ALWAYS
```


#####2.1.4AOP具体事例(一般打印日志，进行切面访问)

```
package com.lemon.chen.aop;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * Created by chenhualong on 2017/7/7.
 *
 * 在切入点前的操作，按order的值由小到大执行
   在切入点后的操作，按order的值由大到小执行
 */

@Aspect
@Order(5)
@Component
public class AccessAspect {


    private Logger logger = Logger.getLogger(getClass());

    ThreadLocal<Long> startTime = new ThreadLocal<>();


    /**
     * 切入点 ， 切入web包下面所有的方法
     */
    @Pointcut("execution(public * com.lemon.chen.web..*.*(..))")
    public void webLog(){}


    /**
     * 执行对应方法前答应出所有的消息
     * @param joinPoint
     * @throws Throwable
     */
    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        startTime.set(System.currentTimeMillis());

        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // 记录下请求内容
        logger.info("URL : " + request.getRequestURL().toString());
        logger.info("HTTP_METHOD : " + request.getMethod());
        logger.info("IP : " + request.getRemoteAddr());
        logger.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        logger.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));

    }

    /**
     * 返回回来的数据进行统计分析
     * @param ret
     * @throws Throwable
     */
    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容
        logger.info("RESPONSE : " + ret);
        logger.info("SPEND TIME : " + (System.currentTimeMillis() - startTime.get()));
    }

}

```

#### 2.2 使用Logback进行日志管理

#####2.2.1  使用通用的logback.xml

```
<configuration  scanPeriod="30 seconds" debug="false">


    <!--首先定义好日志的地址-->
    <property name="logger.root" value="/Users/chenhualong/Documents/dev_tool/test_log/"/>
    <property name="appname" value="Study113"/>


    <!--定义输出到控制台-->
    <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
        <!-- encoders are assigned the type
             ch.qos.logback.classic.encoder.PatternLayoutEncoder by default -->
        <encoder>
            <pattern>%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n</pattern>
        </encoder>
    </appender>

    <!--定义web-error日志-->
    <appender name="web-error" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <file>${logger.root}/${appname}-web-error.log</file>
        <filter class="ch.qos.logback.classic.filter.LevelFilter">
            <level>DEBUG</level>
        </filter>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <fileNamePattern>
                ${logger.root}/${appname}-web-error.log.%d{yyyy-MM-dd}.%i
            </fileNamePattern>
            <maxHistory>30</maxHistory>
            <timeBasedFileNamingAndTriggeringPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP">
                <maxFileSize>100MB</maxFileSize>
            </timeBasedFileNamingAndTriggeringPolicy>
        </rollingPolicy>
        <encoder>
            <pattern>%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n</pattern>
        </encoder>
    </appender>

    <!--定义service-error日志-->
    <appender name="service-error" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <file>${logger.root}/${appname}-service-error.log</file>
        <filter class="ch.qos.logback.classic.filter.LevelFilter">
            <level>DEBUG</level>
        </filter>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <fileNamePattern>
                ${logger.root}/${appname}-service-error.log.%d{yyyy-MM-dd}.%i
            </fileNamePattern>
            <maxHistory>30</maxHistory>
            <timeBasedFileNamingAndTriggeringPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP">
                <maxFileSize>100MB</maxFileSize>
            </timeBasedFileNamingAndTriggeringPolicy>
        </rollingPolicy>
        <encoder>
            <pattern>%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n</pattern>
        </encoder>
    </appender>

    <!--定义dao-error日志-->
    <appender name="dao-error" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <file>${logger.root}/${appname}-dao-error.log</file>
        <filter class="ch.qos.logback.classic.filter.LevelFilter">
            <level>DEBUG</level>
        </filter>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <fileNamePattern>
                ${logger.root}/${appname}-dao-error.log.%d{yyyy-MM-dd}.%i
            </fileNamePattern>
            <maxHistory>30</maxHistory>
            <timeBasedFileNamingAndTriggeringPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP">
                <maxFileSize>100MB</maxFileSize>
            </timeBasedFileNamingAndTriggeringPolicy>
        </rollingPolicy>
        <encoder>
            <pattern>%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n</pattern>
        </encoder>
    </appender>

    <appender name="web-info" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <file>${logger.root}/${appname}-web-info.log</file>
        <filter class="ch.qos.logback.classic.filter.LevelFilter">
            <level>DEBUG</level>
        </filter>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <fileNamePattern>
                ${logger.root}/${appname}-web-info.log.%d{yyyy-MM-dd}.%i
            </fileNamePattern>
            <maxHistory>30</maxHistory>
            <timeBasedFileNamingAndTriggeringPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP">
                <maxFileSize>100MB</maxFileSize>
            </timeBasedFileNamingAndTriggeringPolicy>
        </rollingPolicy>
        <encoder>
            <pattern>%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n</pattern>
        </encoder>
    </appender>

    <appender name="service-info" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <file>${logger.root}/${appname}-service-info.log</file>
        <filter class="ch.qos.logback.classic.filter.LevelFilter">
            <level>DEBUG</level>
        </filter>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <fileNamePattern>
                ${logger.root}/${appname}-service-info.log.%d{yyyy-MM-dd}.%i
            </fileNamePattern>
            <maxHistory>30</maxHistory>
            <timeBasedFileNamingAndTriggeringPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP">
                <maxFileSize>100MB</maxFileSize>
            </timeBasedFileNamingAndTriggeringPolicy>
        </rollingPolicy>
        <encoder>
            <pattern>%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n</pattern>
        </encoder>
    </appender>


    <appender name="dao-info" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <file>${logger.root}/${appname}-dao-info.log</file>
        <filter class="ch.qos.logback.classic.filter.LevelFilter">
            <level>DEBUG</level>
        </filter>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <fileNamePattern>
                ${logger.root}/${appname}-dao-info.log.%d{yyyy-MM-dd}.%i
            </fileNamePattern>
            <maxHistory>30</maxHistory>
            <timeBasedFileNamingAndTriggeringPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP">
                <maxFileSize>100MB</maxFileSize>
            </timeBasedFileNamingAndTriggeringPolicy>
        </rollingPolicy>
        <encoder>
            <pattern>%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n</pattern>
        </encoder>
    </appender>


    <logger name="com.lemon.chen.web" level="DEBUG"  additivity="false">

        <appender-ref ref="web-info" />
        <appender-ref ref="STDOUT" />
    </logger>

    <logger name="com.lemon.chen.service" level="INFO"  additivity="false">

        <appender-ref ref="service-info" />
        <appender-ref ref="STDOUT" />
    </logger>


    <!--控制整体，还是不要debug,boot的日志非常多-->
    <root level="info">
        <appender-ref ref="STDOUT" />
    </root>
</configuration>
```
#### 2.2.2  在代码中使用

```
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
```
#### 2.3 Spring boot  使用jdbctemplate
##### 2.3.1 增加maven的依赖

```
<!--jdbc链接-->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-jdbc</artifactId>
		</dependency>
		<dependency>
			<groupId>mysql</groupId>
			<artifactId>mysql-connector-java</artifactId>
			<version>5.1.21</version>
		</dependency>
```

##### 2.3.2 增加application的配置
```
spring.datasource.primary.url=jdbc:mysql://localhost:3306/test
spring.datasource.primary.username=root
spring.datasource.primary.password=root
spring.datasource.primary.driver-class-name=com.mysql.jdbc.Driver

spring.datasource.secondary.url=jdbc:mysql://localhost:3306/test
spring.datasource.secondary.username=root
spring.datasource.secondary.password=root
spring.datasource.secondary.driver-class-name=com.mysql.jdbc.Driver

```

##### 2.3.3 形成配置注入spring容器

```
package com.lemon.chen;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 *
 */
@Configuration
public class DataSourceConfig {

    @Bean(name = "primaryDataSource")
    @Qualifier("primaryDataSource")
    @ConfigurationProperties(prefix="spring.datasource.primary")
    public DataSource primaryDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "secondaryDataSource")
    @Qualifier("secondaryDataSource")
    @Primary
    @ConfigurationProperties(prefix="spring.datasource.secondary")
    public DataSource secondaryDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "primaryJdbcTemplate")
    public JdbcTemplate primaryJdbcTemplate(
            @Qualifier("primaryDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean(name = "secondaryJdbcTemplate")
    public JdbcTemplate secondaryJdbcTemplate(
            @Qualifier("secondaryDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

}
```

##### 2.3.4 实际使用jdbctemplate进行库表操作

```
package com.lemon.chen.service;

import com.lemon.chen.modul.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by chenhualong on 2017/7/10.
 *
 *
 * 注意， 请在各个业务上加载Transactional,不要单独的一个增删改查上面加
 */

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Transactional
    @Override
    public void create(String name, Integer age) {



        String sql = "insert into mb_user(userid,username,age) VALUES (?,?,?)";


        //第一种塞入方法
        jdbcTemplate.update(sql, new Object[]{1, name, age});

        //第二种塞入方法
        jdbcTemplate.update(sql, 2, name, age);

        //第三种塞入方法

        jdbcTemplate.update(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement) throws SQLException {


                preparedStatement.setInt(1, 3);
                preparedStatement.setString(2, "lemon");
                preparedStatement.setInt(3, 44);
            }
        });


    }

    @Transactional
    @Override
    public void deleteByName(String name) {

        jdbcTemplate.update("DELETE from MB_USER WHERE username=?","lemon");

    }

    @Override
    public Integer getAllUsers() {

        final User user = new User();
        String sql = "SELECT userid, username,age from mb_user where userid=?";

        try {
            jdbcTemplate.query(sql, new Object[]{1}, new RowCallbackHandler() {

                @Override
                public void processRow(ResultSet rs) throws SQLException {

                    user.setUserId(rs.getInt(1));
                    user.setUserName(rs.getString(2));
                    user.setAge(rs.getInt(3));
                }
            });
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            //这类异常不用处理， 因为相当于没有这个数据而已
        }

        return 1;
    }

    @Transactional
    @Override
    public void deleteAllUsers() {

        jdbcTemplate.update("DELETE FROM MB_USER");

       // throw new RuntimeException("假如我逻辑错误");
    }

    @Transactional
    @Override
    public void deleteAllUsersFroTransaction() {

        jdbcTemplate.update("DELETE FROM MB_USER");

        throw new RuntimeException("假如我逻辑错误");
    }
}
```

###### 2.3.5 单元测试一把

```
package com.lemon.chen;

import com.lemon.chen.service.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Study114ApplicationTests {


    @Autowired
    private UserServiceImpl userService;


    @Test
    public void test() {


        userService.deleteAllUsers();

        userService.create("lemon1", 12);

    }

    /**
     * 测试事务,测试成功，数据表刘
     */
    @Test
    public void test2(){

        userService.deleteAllUsersFroTransaction();
    }
}
```

#### 2.4 spring boot进行监控
##### 2.4.1 增加maven的依赖

```
            <dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-actuator</artifactId>
		</dependency>
```

#####2.4.2 进行application的设置

```
关闭权限配置，一般敏感信息不让访问
management.security.enabled=false
```


##### 2.4.3 进行监控连接的访问
```
ID	描述	敏感（Sensitive）
autoconfig	显示一个auto-configuration的报告，该报告展示所有auto-configuration候选者及它们被应用或未被应用的原因	true
beans	显示一个应用中所有Spring Beans的完整列表	true
configprops	显示一个所有@ConfigurationProperties的整理列表	true
dump	执行一个线程转储	true
env	暴露来自Spring　ConfigurableEnvironment的属性	true
health	展示应用的健康信息（当使用一个未认证连接访问时显示一个简单的’status’，使用认证连接访问则显示全部信息详情）	false
info	显示任意的应用信息	false
metrics	展示当前应用的’指标’信息	true
mappings	显示一个所有@RequestMapping路径的整理列表	true
shutdown	允许应用以优雅的方式关闭（默认情况下不启用）	true
trace	显示trace信息（默认为最新的一些HTTP请求）	true
```
#### 2.5 spring boot集成rabbitmq

#####2.5.1 增加maven依赖

```
<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-amqp</artifactId>
		</dependency>


		<!-- https://mvnrepository.com/artifact/commons-lang/commons-lang -->
		<dependency>
			<groupId>commons-lang</groupId>
			<artifactId>commons-lang</artifactId>
			<version>2.6</version>
		</dependency>
		<dependency>
			<groupId>com.alibaba</groupId>
			<artifactId>fastjson</artifactId>
			<version>1.2.29</version>
		</dependency>
		<dependency>
			<groupId>commons-httpclient</groupId>
			<artifactId>commons-httpclient</artifactId>
			<version>3.1</version>
		</dependency>
		<dependency>
			<groupId>commons-beanutils</groupId>
			<artifactId>commons-beanutils</artifactId>
			<version>1.8.3</version>
		</dependency>
```
##### 2.5.2 增加application的配置

```
spring.application.name=lemon-rabbitmq
spring.rabbitmq.host=10.55.120.247
spring.rabbitmq.port=5672
spring.rabbitmq.username=root
spring.rabbitmq.password=root
spring.rabbitmq.virtualHost=/
spring.rabbitmq.publisher-confirms=true
```

#####2.5.3 实现工厂，进行reabbitmq连接

```
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

```
#####2.5.4  生产者是这个样子的

```
package com.lemon.chen.rabbitUtil;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;
import com.sun.deploy.util.StringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenhualong on 2017/8/7.
 */
public class RabbitMqProvider {


    public static void main(String[] args) throws Throwable{


        /**
         * 如果消息体有空格，尽量的全部去除，不然一条消息体读不完全
         */
        String message ="info def ghi jkl mno pqr stu vwx yz";

        /**
         * 第一步，建立broker，连接rabbitmq
         */
        Connection connection  = RabbitmqFactoryUtil.getConnection();

        /**
         * 第二步，获取信道
         */

        Channel channel = connection.createChannel(); //声明消息通道


        /**
         * 第三步，声明交换器
         */

        RabbitMqExchange exchange = RabbitMqExchange.DIRECT;

        /**
         * 第四步，把队列和交换器进行绑定起来
         */
        switch (exchange){

            case DEFAULT:
                //默认，向指定的队列发送消息，
                // 消息只会被一个consumer处理,多个消费者消息会轮训处理,
                // 消息发送时如果没有consumer，消息不会丢失

                //为消息通道绑定一个队列
                //队列的相关参数需要与第一次定义该队列时相同，否则会出错
                //参数1：队列名称
                //参数2：为true时server重启队列不会消失
                //参数3：队列是否是独占的，如果为true只能被一个connection使用，其他连接建立时会抛出异常
                //参数4：队列不再使用时是否自动删除（没有连接，并且没有未处理的消息)
                //参数5：建立队列时的其他参数
                channel.queueDeclare(RabbitMqQueue.DEFAULT_QUEUE, true, false, true, null);
                /**
                 * 开始发送信息
                 */

                //向server发布一条消息
                //参数1：exchange名字，若为空则使用默认的exchange
                //参数2：routing key
                //参数3：其他的属性
                //参数4：消息体
                //RabbitMQ默认有一个exchange，叫default exchange，它用一个空字符串表示，它是direct exchange类型，
                //任何发往这个exchange的消息都会被路由到routing key的名字对应的队列上，如果没有对应的队列，则消息会被丢弃
                channel.basicPublish("", RabbitMqQueue.DEFAULT_QUEUE, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes()); //设置消息为持久化，服务器重启不会丢失
                System.out.println("发送default信息:" + message);
                break ;

            case FANOUT:
                //广播给所有队列  接收方也必须通过fanout交换机获取消息,所有连接到该交换机的consumer均可获取消息
                //如果producer在发布消息时没有consumer在监听，消息将被丢弃


                //定义一个交换机
                //参数1：交换机名称
                //参数2：交换机类型
                //参数3：交换机持久性，如果为true则服务器重启时不会丢失
                //参数4：交换机在不被使用时是否删除
                //参数5：交换机的其他属性
                /**
                 * 开始消费信息
                 */
                channel.exchangeDeclare(RabbitMqQueue.FANOUT_MONEY_QUEUE, "fanout", false, true, null);

                channel.basicPublish(RabbitMqQueue.FANOUT_MONEY_QUEUE, "", null, message.getBytes());

                System.out.println("发送fanout信息:" + message);

                break ;

            case DIRECT:
                //向所有绑定了相应routing key的队列发送消息
                //如果producer在发布消息时没有consumer在监听，消息将被丢弃
                //如果有多个consumer监听了相同的routing key  则他们都会受到消息
                channel.exchangeDeclare(RabbitMqQueue.DIRECT_MONEY_QUEUE, "direct", false, true, null);

                /**
                 * 设置路由
                 */
                String[] temp = StringUtils.splitString(message, " ");
                channel.basicPublish(RabbitMqQueue.DIRECT_MONEY_QUEUE, temp[0], null, temp[1].getBytes());
                System.out.println("发送direct信息: " + message);

                /**
                 * 开始消费信息
                 */
                break ;

            case TOPIC:
                //与direct模式有类似之处，都使用routing key作为路由
                //不同之处在于direct模式只能指定固定的字符串，而topic可以指定一个字符串模式
                /**
                 * 开始消费信息
                 */

                channel.exchangeDeclare(RabbitMqQueue.TOPIC_MONEY_QUEUE, "topic", false, true, null);

                String[] temp2 = StringUtils.splitString(message, " ");
                channel.basicPublish(RabbitMqQueue.TOPIC_MONEY_QUEUE, temp2[0], null, temp2[1].getBytes());
                System.out.println("发送topic信息: " + message);

                break ;

            case HEADERS:
                //与topic和direct有一定相似之处，但不是通过routing key来路由消息
                //通过headers中词的匹配来进行路由
                /**
                 * 开始消费信息
                 */

                channel.exchangeDeclare(RabbitMqQueue.HEADER_MONEY_QUEUE, "headers", false, true, null);

                String[] temp3 = StringUtils.splitString(message, " ");

                Map<String, Object> headers = new HashMap<String, Object>();
                headers.put("name", temp3[0]); //定义headers
                headers.put("sex", temp3[1]);
                AMQP.BasicProperties.Builder builder = new AMQP.BasicProperties.Builder().headers(headers);

                channel.basicPublish(RabbitMqQueue.HEADER_MONEY_QUEUE, "", builder.build(), temp3[2].getBytes()); //根据headers路由到相应的consumer
                System.out.println("发送headers信息: " + message);
                break ;
        }

        /**
         * 第五步， 关闭信道，关闭连接
         */
        RabbitmqFactoryUtil.closeConnection(connection,channel);


    }
}
```

##### 2.5.5 消费者是这个样子的

```
package com.lemon.chen.rabbitUtil;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenhualong on 2017/8/7.
 */
public class RabbitMqConsumer {

    public static void main(String[] args) throws Throwable{


         String queueName ="";
        /**
         * 第一步，建立broker，连接rabbitmq
         */
        Connection connection  = RabbitmqFactoryUtil.getConnection();

        /**
         * 第二步，获取信道
         */

        Channel channel = connection.createChannel(); //声明消息通道

        /**
         * 第三步，声明交换器
         */

        RabbitMqExchange exchange = RabbitMqExchange.DIRECT;


        /**
         * 第四步，把队列和交换器进行绑定起来
         */
        switch (exchange){

            case DEFAULT:
                //队列的相关参数需要与第一次定义该队列时相同，否则会出错，
                // 使用channel.queueDeclarePassive()可只被动绑定已有队列，而不创建
                channel.queueDeclare(RabbitMqQueue.DEFAULT_QUEUE, true, false, true, null);
                break ;

            case FANOUT:
                //广播给所有队列  接收方也必须通过fanout交换机获取消息,所有连接到该交换机的consumer均可获取消息
                //如果producer在发布消息时没有consumer在监听，消息将被丢弃


                //定义一个交换机
                //参数1：交换机名称
                //参数2：交换机类型
                //参数3：交换机持久性，如果为true则服务器重启时不会丢失
                //参数4：交换机在不被使用时是否删除
                //参数5：交换机的其他属性
                channel.exchangeDeclare(RabbitMqQueue.FANOUT_MONEY_QUEUE, "fanout", false, true, null);
                //声明一个临时队列，该队列会在使用完比后自动销毁
                //将队列绑定到交换机,参数3无意义此时
                channel.queueBind(channel.queueDeclare().getQueue(), RabbitMqQueue.FANOUT_MONEY_QUEUE, "");
                break ;

            case DIRECT:
                //向所有绑定了相应routing key的队列发送消息
                //如果producer在发布消息时没有consumer在监听，消息将被丢弃
                //如果有多个consumer监听了相同的routing key  则他们都会受到消息
                channel.exchangeDeclare(RabbitMqQueue.DIRECT_MONEY_QUEUE, "direct", false, true, null);
                queueName = channel.queueDeclare().getQueue();
                channel.queueBind(queueName, RabbitMqQueue.DIRECT_MONEY_QUEUE, "info"); //绑定一个routing key，可以绑定多个
                channel.queueBind(queueName, RabbitMqQueue.DIRECT_MONEY_QUEUE, "warning");
                break ;

            case TOPIC:
                //与direct模式有类似之处，都使用routing key作为路由
                //不同之处在于direct模式只能指定固定的字符串，而topic可以指定一个字符串模式

                channel.exchangeDeclare(RabbitMqQueue.TOPIC_MONEY_QUEUE, "topic", false, true, null);
                queueName = channel.queueDeclare().getQueue();
                channel.queueBind(queueName, RabbitMqQueue.TOPIC_MONEY_QUEUE, "warning.#"); //监听两种模式 #匹配一个或多个单词 *匹配一个单词
                channel.queueBind(queueName, RabbitMqQueue.TOPIC_MONEY_QUEUE, "*.blue");
                break ;

            case HEADERS:
                //与topic和direct有一定相似之处，但不是通过routing key来路由消息
                //通过headers中词的匹配来进行路由

                channel.exchangeDeclare(RabbitMqQueue.HEADER_MONEY_QUEUE, "headers", false, true, null);
                queueName = channel.queueDeclare().getQueue();
                Map<String, Object> headers = new HashMap<String, Object>() {{
                    put("name", "test");
                    put("sex", "male");
                    put("x-match", "any");//all==匹配所有条件，any==匹配任意条件
                }};
                channel.queueBind(queueName, RabbitMqQueue.HEADER_MONEY_QUEUE, "", headers);

                break ;

        }

        // 在同一时间不要给一个worker一个以上的消息。
        // 不要将一个新的消息分发给worker知道它处理完了并且返回了前一个消息的通知标志（acknowledged）
        // 替代的，消息将会分发给下一个不忙的worker。
        channel.basicQos(1); //server push消息时的队列长度

        //用来缓存服务器推送过来的消息
        QueueingConsumer consumer = new QueueingConsumer(channel);

        //为channel声明一个consumer，服务器会推送消息
        //参数1:队列名称
        //参数2：是否发送ack包，不发送ack消息会持续在服务端保存，直到收到ack。  可以通过channel.basicAck手动回复ack
        //参数3：消费者
        channel.basicConsume(queueName, false, consumer);

        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            System.out.println("Received " + new String(delivery.getBody()));

            //回复ack包，如果不回复，消息不会在服务器删除
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            //channel.basicReject(); channel.basicNack(); //可以通过这两个函数拒绝消息，可以指定消息在服务器删除还是继续投递给其他消费者
        }

    }
}

```



