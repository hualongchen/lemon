package com.lemon.chen.controller.user;


import com.lemon.chen.controller.user.form.LoginForm;
import com.lemon.chen.controller.user.form.RegistForm;
import com.lemon.chen.util.ResultVO;
import lombok.val;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@RestController
public class UserController {


    @PostMapping("/register")
    public ResultVO registUser(@Validated RegistForm form) {


        /**
         *  第一步：系统的异常类自动处理并返回
         */

        /**
         * 第二步：进行查库校验是否存在
         */

        /**
         * 第三步： 存储信息，加密密码等重要信息，然后返回id
         */


        return new ResultVO();

    }


    @PostMapping("/login")
    public ResultVO loginUser(@Validated LoginForm form) {

        /**
         * 第一步 ： 系统校验，有异常自动进行处理并返回
         */

        /**
         * 第二步： 之前应该有filter进行拦截登陆，并校验其登陆次数，防止暴力破解
         */

        /**
         * 第三步： 取session的值，判断是否是空的，然后逻辑验证
         */
        /**
         * 第四步：进行查库，验证密码和用户是否正确
         */

        /**
         * 第五步： 操作cookie,进行加密操作，然后放入到缓存中. 如果使用spring-session则不用考虑
         */

        /**
         * 第六步，将cookie设置为httponly，并传输加密，里面需设置过期时间为半个小时。
         */


        val kiss = 100;

        val resdfsd= new ResultVO();

        resdfsd.getCode();

        return new ResultVO();
    }

    /**
     * 进行session清除
     *
     * @param request
     * @return
     */
    public Object loginOut(HttpServletRequest request) {


        HttpSession session = request.getSession();

        session.removeAttribute("user");

        return new ResultVO();

    }


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


}
