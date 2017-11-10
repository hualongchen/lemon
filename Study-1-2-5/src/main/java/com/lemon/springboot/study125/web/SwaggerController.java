package com.lemon.springboot.study125.web;


import com.lemon.springboot.study125.modul.UserForm;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chenhualong
 * 1. 进行swagger的展示    :http://localhost:9000/swagger-ui.html
 * 2. controller的各种用法
 */

@RestController
@RequestMapping(value = "/user")
public class SwaggerController {




    /**
     * 增加测试数据
     */
    public static List<UserForm> userMap = new ArrayList<UserForm>();

    static {


        UserForm form = new UserForm();
        form.setUserAge(1);
        form.setUserName("lemon" + 1);
        form.setUserId(1);
        userMap.add(form);
        form = new UserForm();
        form.setUserAge(2);
        form.setUserName("lemon" + 2);
        form.setUserId(2);
        userMap.add(form);
        form = new UserForm();
        form.setUserAge(3);
        form.setUserName("lemon" + 3);
        form.setUserId(3);
        userMap.add(form);


    }

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

    /**
     * 利用 requestmethod DELETE 进行删除
     *
     * @param id 通过
     * @return
     */

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String deleteUser(@PathVariable long id) {

        System.out.println("delete the userId  ：" + id);
        return "success";
    }


    /**
     * 直接查询全局
     * 通过
     *
     * @return
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<UserForm> getUserList() {

        return userMap;
    }

    /**
     * 利用postMaping结合，更方便
     *
     * @param id 通过
     * @return
     */
    @PostMapping(value = "/find2")
    public Object findUser(@RequestParam int id) {

        System.out.println("PostMapping userId :" + id);

        return id;
    }

    /**
     * 通过
     *
     * @param id
     * @return
     */
    @GetMapping(value = "find3")
    public Object findUser2(@RequestParam int id) {

        System.out.println("GetMapping userId :" + id);

        return id;
    }
}
