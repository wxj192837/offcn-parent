package com.offcn.user.controller;

import com.offcn.user.pojo.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "用户的controller")
public class UserController {

    @ApiOperation(value = "测试方法用来demo")
    @ApiImplicitParams(value={
            @ApiImplicitParam(name="name",value="姓名",required = true),@ApiImplicitParam(name="uid",value="用户id")
    })
    @GetMapping("/hello")
    public String demo(String name){
        return "Hi "+name+",this is a demo!";
    }

    @ApiOperation("保存用户")
    @ApiImplicitParams(value={
            @ApiImplicitParam(name="name",value="姓名",required = true),@ApiImplicitParam(name="address",value="地址")
    })
    @PostMapping("/user")
    public User save(String name, String address){
        User user  = new User();
        user.setName(name);
        user.setAddress(address);
        return user;
    }
}
