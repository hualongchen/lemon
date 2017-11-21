package com.tr.file.web.controller;


import com.tr.file.modul.user.LoginForm;
import com.tr.file.util.ErrorContent;
import com.tr.file.util.ResultVO;
import com.tr.file.util.tencent.TencentFileConfig;
import com.tr.file.util.tencent.TencentFileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class UserController {



    /**
     * 进行登陆的路由判断
     *
     * @param request
     * @return
     */
    @GetMapping("/")
    public String isLogin(HttpServletRequest request) {

        HttpSession session = request.getSession();

        if (null == session.getAttribute("TR_USER")) {

            return "login";
        }

        return "file";
    }


    /**
     * 登陆 /验证
     */

    @PostMapping("/login")
    @ResponseBody
    public Object userLogin(LoginForm form, HttpServletRequest request) {


        HttpSession session = request.getSession();

        /**
         * 已经登陆
         */
        if (null != session.getAttribute("TR_USER")) {

            return new ResultVO();
        }

        /**
         * 如果没有登陆，直接验证密码
         */

        if (form.getPassword().equals("test") && form.getUserName().equals("admin")) {

            session.setAttribute("TR_USER", form);

            return new ResultVO();
        }

        /**
         * 验证失败
         */
        return new ResultVO<>(ErrorContent.LOGIN_USERNAME_ERROR_CODE, ErrorContent.LOGIN_USERNAME_ERROR_MESSAGE);

    }


    /**
     * 测试不同浏览器
     *
     * @param request
     * @return
     */
    @GetMapping("/test")
    @ResponseBody
    public Object testUserController(HttpServletRequest request) {

        HttpSession session = request.getSession();

        System.out.println("sessionId is ======:" + session.getId());


        TencentFileUtil util = new TencentFileUtil();

        return new ResultVO<>();

    }

}
