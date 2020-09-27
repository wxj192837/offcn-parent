package com.offcn.user.controller;

import com.offcn.response.AppResponse;
import com.offcn.user.pojo.TMemberAddress;
import com.offcn.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jdk.nashorn.internal.ir.ReturnNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(tags = "用户信息模块")
public class UserInfoController {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private UserService userService;

    @ApiOperation("用户信息查询地址")
    @GetMapping("/findAddress")
    public AppResponse<List<TMemberAddress>> findAddress(String accessToken){
        // 是否登录
        String memberId = redisTemplate.opsForValue().get(accessToken);
        if(memberId == null){
            return AppResponse.fail(null);
        }
        // 查询地址
        List<TMemberAddress> addressList = userService.findAddressList(Integer.parseInt(memberId));
        return AppResponse.ok(addressList);
    }
}
