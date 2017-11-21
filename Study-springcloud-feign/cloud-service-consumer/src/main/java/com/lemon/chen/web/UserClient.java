package com.lemon.chen.web;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by chenhualong on 2017/7/24.
 */

/**
 * 这里匹配对应的服务
 */
@FeignClient("user-service-provider")
public interface UserClient {


    /**
     * 这里和微服务的请求进行匹配
     * @return
     */
    @GetMapping("/hello")
    String consumer();


}
