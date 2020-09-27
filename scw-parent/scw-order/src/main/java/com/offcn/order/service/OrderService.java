package com.offcn.order.service;


import com.offcn.order.pojo.TOrder;
import com.offcn.order.vo.req.OrderSubmitVo;

public interface OrderService {
    TOrder saveOrder(OrderSubmitVo vo);
}
