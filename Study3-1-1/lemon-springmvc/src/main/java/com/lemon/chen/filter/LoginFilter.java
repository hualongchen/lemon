package com.lemon.chen.filter;


import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by chenhualong on 16/6/21.
 */
public class LoginFilter extends OncePerRequestFilter {


    /**
     * 进行登录校验
     * @param request res
     * @param response req
     * @param filterChain  fil
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        try {


            String url = request.getRequestURI().toString();

            System.out.println("--------->this is loginFilter");

            if(url.contains("page2")){

                request.setCharacterEncoding("UTF-8");
                response.setCharacterEncoding("UTF-8");
                request.getRequestDispatcher("/page").forward(request,response);
            }else{

                filterChain.doFilter(request, response);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
