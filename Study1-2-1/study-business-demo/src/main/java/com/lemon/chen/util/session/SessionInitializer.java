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
