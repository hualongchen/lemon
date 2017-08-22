# 3-3-2 Spring boot整合Spring session处理登陆状态

### 1.0问题背景

>HttpSession是通过Servlet容器创建和管理的，像Tomcat/Jetty都是保存在内存中的。而如果我们把web服务器搭建成分布式的集群，然后利用LVS或Nginx做负载均衡，那么来自同一用户的Http请求将有可能被分发到两个不同的web站点中去。那么问题就来了，如何保证不同的web站点能够共享同一份session数据呢,这个时候就需要共享Session了。

### 2.0 解决方案列表

* 基于resin/tomcat web容器本身的session复制机制
* 基于NFS共享文件系统
* 基于Cookie进行session共享
* 基于数据库的Session共享
* 基于分布式缓存的Session共享，如memcached，Redis，jbosscache
* 基于ZooKeeper的Session共享
* 基于spring session进行Session共享


### 3.0 其他方案的简介

#### 3.1 使用cookie进行解决

登录验证后，创建登录凭证（比如：用户id+登录时间+过期时间），将登录凭证进行加密（为了避免暴露信息），加密后写到浏览器的cookie，以后，每次请求都发送cookie，服务器根据对应的解密算法对其进行验证（或者将加密过的cookie内容存储到数据库，请求服务器的时候，服务器在数据库进行查找）

** 需要注意**

- 通过设置httpOnly属性，这样cookie只在http中传输，而不会被脚本窃取，但是网络拦截http请求还是会得到cookie。
- 在cookie中加入校验信息，这个校验信息与用户的使用环境相关，比如ip地址，计算机的物理地址等，在服务器端对校验的时候，如果校验值发生了变化，则要求重新登录。
- 最好还是加入https ,防止网络劫持，加密传输。

#### 3.2  Session 复制解决

Session复制
       该种方式下，负载均衡器会根据各个node的状态，把每个request进行分发，使用这样的测试，必须在多个node之间复制用户的session，实时保持整个集群中用户的状态同步。其中jboss的实现原理是使用拦截器，根据用户的同步策略拦截request，做完同步处理后再交给server产生响应。

       优点：session不会被绑定到具体的node，只要有一个node存活，用户状态就不会丢失，集群能够正常工作。

       缺点：node之间通信频繁，响应速度有影响，高并发情况下性能下降比较厉害。

#### 3.3 Session粘性
       该种方式下，当用户发出第一个request后，负载均衡器动态的把该用户分配至到某个节点，并记录该节点的jvm路由，以后该用户的所有的request都会绑定到这个jvm路由，用户只会和该server交互。

       优点：响应速度快，多个节点之间无需通信

       缺点：某个node死掉之后，它负责的所有用户都会丢失session。

改进：servlet容器在新建、更新或维护session时，向其它no de推送修改信息。这种方式在高并发情况下同样会影响效率。

       以上这两种方式都需要负载均衡器和Servlet容器的支持，在部署时需要单独配置负载均衡器和Servelt容器。 

#### 3.4 基于分布式缓存的session共享机制
       将会话Session统一存储在分布式缓存中，并使用Cookie保持客户端和服务端的联系，每一次会话开始就生成一个GUID作为SessionID，保存在客户端的Cookie中，在服务端通过SessionID向分布式缓存中获取session

### 4.0 使用Spring-session进行处理


#### 4.1  前期redis准备

**备注**  redis必须使用redis 2.8版本以上，否则会报错误。

1. 下载redis : http://www.redis.net.cn/download/
2. 进行编译和测试： make     make test
3. 进行启动  ./redis-server
4. 进行关闭 ./redis-cli    进入后输入SHUTDOWN

#### 4.2 maven 包进行准备

```
<!--利用spring-serssion管理会话-->
        <dependency>
            <groupId>org.springframework.session</groupId>
            <artifactId>spring-session-data-redis</artifactId>
            <version>1.3.1.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-redis</artifactId>
            <version>1.4.7.RELEASE</version>
        </dependency>
```
#### 4.3 进行application的配置

```
spring.redis.database= 1
spring.redis.host=localhost
spring.redis.password=
spring.redis.port=6379
spring.redis.timeout=11
spring.redis.pool.max-idle=8
spring.redis.pool.min-idle=0
spring.redis.pool.max-active=8
spring.redis.pool.max-wait=-1
指定默认session缓存方式，redis
spring.session.store-type=redis

```

####4.4 整合spring session
```
package com.lemon.chen.util.session;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import java.lang.reflect.Method;

/**
 * 利用spring-session 解决session共享的问题
 */
@Configuration
@EnableCaching
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 1800)
//开启spring session支持  实现session共享,设置为1800秒
public class RedisSessionConfig extends CachingConfigurerSupport {

    @Autowired
    private RedisForm redis;

    private int expireTime = 60 * 60;

    /**
     * 生成key的策略
     *
     * @return
     */
    @Bean
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuilder sb = new StringBuilder();
                sb.append(target.getClass().getName());
                sb.append(method.getName());
                for (Object obj : params) {
                    sb.append(obj.toString());
                }
                return sb.toString();
            }
        };
    }

    /**
     * 设置redis连接的factory
     *
     * @return
     */
    @Bean
    public JedisConnectionFactory redisConnectionFactory() {
        JedisConnectionFactory factory = new JedisConnectionFactory();
        factory.setHostName(redis.getHost());
        factory.setPort(redis.getPort());
        factory.setTimeout(redis.getTimeout()); //设置连接超时时间
        return factory;
    }

    /**
     * 设置缓存超时时间
     *
     * @param redisTemplate
     * @return
     */
    @Bean
    public CacheManager cacheManager(RedisTemplate redisTemplate) {
        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
        //Number of seconds before expiration. Defaults to unlimited (0)
        cacheManager.setDefaultExpiration(expireTime); //设置key-value超时时间
        return cacheManager;
        //return new RedisCacheManager(redisTemplate);
    }

    @SuppressWarnings("unchecked")
    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
        StringRedisTemplate template = new StringRedisTemplate(factory);
        setSerializer(template); //设置序列化工具，这样ReportBean不需要实现Serializable接口
        template.afterPropertiesSet();
        return template;
    }

    private void setSerializer(StringRedisTemplate template) {
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        template.setValueSerializer(jackson2JsonRedisSerializer);
    }
}


```

#### 4.5 设置加载配置项

```
package com.lemon.chen.util.session;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import lombok.Data;


/**
 * 使用 lombok框架，可以省去写 set  get 方法
 */
@Configuration
@Data
public class RedisForm {

    @Value("${spring.redis.database}")
    private String database;
    @Value("${spring.redis.host}")
    private String host;
    //@Value("${spring.redis.}")
    private String password;
    @Value("${spring.redis.port}")
    private int port;
    @Value("${spring.redis.timeout}")
    private int timeout;
    @Value("${spring.redis.pool.max-idle}")
    private String maxIdle;
    @Value("${spring.redis.pool.min-idle}")
    private String minIdle;
    @Value("${spring.redis.pool.max-active}")
    private String maxActive;
    @Value("${spring.redis.pool.max-wait}")
    private String maxWait;

}
```

#### 4.6 设置session加载地点

```
package com.lemon.chen.util.session;

import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;


/**
 * 使得Servlet容器在每一次请求时都使用我们的springSessionRepositoryFilter过滤器
 */
public class SessionInitializer extends AbstractHttpSessionApplicationInitializer {


    /**
     * 初始化刚刚我们的redis配置
     */
    public SessionInitializer() {
        super(RedisSessionConfig.class);
    }
}

```

#### 4.7 redis操作的工具类

```
package com.lemon.chen.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * redis操作的工具类
 */
@Component
public class RedisUtils {

    @Autowired
    private RedisTemplate redisTemplate;


    /**
     * 批量删除对应的value
     *
     * @param keys
     */
    public void remove(final String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }
    /**
     * 批量删除key
     *
     * @param pattern
     */
    public void removePattern(final String pattern) {
        Set<Serializable> keys = redisTemplate.keys(pattern);
        if (keys.size() > 0)
            redisTemplate.delete(keys);
    }
    /**
     * 删除对应的value
     *
     * @param key key
     */
    public void remove(final String key) {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }
    /**
     * 判断缓存中是否有对应的value
     *
     * @param key
     * @return
     */
    public boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }
    /**
     * 读取缓存
     *
     * @param key
     * @return
     */
    public Object get(final String key) {
        Object result = null;
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        result = operations.get(key);
        return result;
    }
    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, Object value) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, Object value, Long expireTime) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}

```

#### 4.8 测试一下

```
 /**
     * 测试我们的session是否通信正常
     *
     * @param request
     * @param response
     * @return
     */

    @GetMapping("user")
    @ResponseBody
    public Object testSession(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession();

        System.out.println("首次访问，存入的ID为" + session.getId());

        LoginForm form = new LoginForm();

        form.setNickName("lemon");
        form.setPassWord("123456");

        session.setAttribute("user", form);

        LoginForm returnForm = (LoginForm) session.getAttribute("user");

        return returnForm.toString();

    }


    /**
     * 打一下端口，看是否解决session共享问题
     *
     * @param request
     * @param response
     * @return
     */
    @GetMapping("user2")
    @ResponseBody
    public Object getUserInfo(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession();

        System.out.println("此项目sessionID是:" + session.getId());

        System.out.println("此项目所在的端口是:" + request.getLocalPort());
        System.out.println("此项目所在的端口是:" + request.getRemotePort());
        System.out.println("此项目所在的端口是:" + request.getServerPort());

        LoginForm returnForm = (LoginForm) session.getAttribute("user");

        return returnForm.toString();
    }

```