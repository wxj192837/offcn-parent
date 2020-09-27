package com.offcn.order.controller;

import com.offcn.order.pojo.TOrder;
import com.offcn.order.service.OrderService;
import com.offcn.order.vo.req.OrderSubmitVo;
import com.offcn.response.AppResponse;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "订单模块")
@RestController
public class OrderController {
    @Autowired
    private StringRedisTemplate redisTemplate;
     @Autowired
     private OrderService orderService;
@PostMapping("createOrder")
public AppResponse<TOrder> createOrder(@RequestBody OrderSubmitVo vo){
          //判断当前用户是否登录
    String memberId = redisTemplate.opsForValue().get(vo.getAccessToken());
    if (memberId==null){
        AppResponse response = AppResponse.fail(null);
        response.setMsg("没有登录");
        return response;
    }
    //已经登录
    try {
        TOrder tOrder = orderService.saveOrder(vo);
        return AppResponse.ok(tOrder);
    } catch (Exception e) {
        e.printStackTrace();
        return AppResponse.
                fail(null);
    }
}
}
