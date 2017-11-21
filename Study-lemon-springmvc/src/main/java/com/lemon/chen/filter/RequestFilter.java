package com.lemon.chen.filter;

import com.lemon.chen.content.ResultVO;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by chenhualong on 16/6/21.
 */
public class RequestFilter extends OncePerRequestFilter{


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        try {

            String url =request.getRequestURI();

            System.out.println("这次的请求流水是：---->"+url);

            //拦截不必要的请求
            if(null==url|| "".equals(url) ||url.contains("favicon.ico")){

                return ;
            }


            if(url.contains("/")){

                //通过
                //增加跨域的请求
                response.setHeader("Access-Control-Allow-Origin", "*");
                response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
                response.setHeader("Access-Control-Max-Age", "3600");
                response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
                filterChain.doFilter(request, response);

            }else{

                //返回拦截错误信息

                ResultVO<String> resultVO = new ResultVO<String>();

                resultVO.setData(url);
                resultVO.setErrorCode(401);
                resultVO.setErrorMsg("您没有权限访问");

                response.setStatus(HttpServletResponse.SC_OK);  //统一返回200
                response.setContentType("application/json;charset=UTF-8"); //返回json时必须设置
                response.getWriter().println(net.sf.json.JSONObject.fromObject(resultVO));

            }


        }catch (Throwable throwable){

            throwable.printStackTrace();
        }
    }
}
