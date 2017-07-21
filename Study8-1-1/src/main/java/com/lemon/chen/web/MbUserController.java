package com.lemon.chen.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lemon.chen.core.Result;
import com.lemon.chen.core.ResultGenerator;
import com.lemon.chen.model.MbUser;
import com.lemon.chen.service.MbUserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
* Created by chenhualong on 2017/07/05.
*/
@RestController
@RequestMapping("/mb/user")
public class MbUserController {
    @Resource
    private MbUserService mbUserService;

    @PostMapping("/add")
    public Result add(MbUser mbUser) {
        mbUserService.save(mbUser);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/delete")
    public Result delete(@RequestParam Integer id) {
        mbUserService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/update")
    public Result update(MbUser mbUser) {
        mbUserService.update(mbUser);
        return ResultGenerator.genSuccessResult();

    }

    @PostMapping("/detail")
    public Result detail(@RequestParam Integer id) {
        MbUser mbUser = mbUserService.findById(id);
        return ResultGenerator.genSuccessResult(mbUser);
    }

    @PostMapping("/list")
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<MbUser> list = mbUserService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
