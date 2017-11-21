package com.example.cloudapigetway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by chenhualong on 2017/7/25.
 * 自定义拦截器，实现公共业务拦截
 */
public class MyFilter extends ZuulFilter {


    /**
     * pre：可以在请求被路由之前调用
      routing：在路由请求时候被调用
      post：在routing和error过滤器之后被调用
      error：处理请求时发生错误时被调用
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 通过int值来定义过滤器的执行顺序
     * @return
     */
    @Override
    public int filterOrder() {
        return 0;
    }


    /**
     * 返回一个boolean类型来判断该过滤器是否要执行，所以通过此函数可实现过滤器的开关。
     * 在上例中，我们直接返回true，所以该过滤器总是生效。
     * @return
     */
    @Override
    public boolean shouldFilter() {

        return true;
    }


    /**
     * 具体的拦截业务逻辑
     * 需要进行try  catch 。
     * @return
     */
    @Override
    public Object run() {

        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        try{
            //Object accessToken = request.getParameter("accessToken");

            System.out.println("========我就过一下楼=========");

            /**
             * ctx.setSendZuulResponse(false);
             ctx.setResponseStatusCode(401);
             */
        }catch (Exception e){

            ctx.set("error.status_code", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            ctx.set("error.exception", e);
        }

        return null;

    }
}
