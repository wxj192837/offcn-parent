package com.offcn.order.service.impl;


import com.offcn.enums.OrderStatusEnume;
import com.offcn.order.mapper.TOrderMapper;
import com.offcn.order.pojo.TOrder;
import com.offcn.order.service.OrderService;
import com.offcn.order.service.ProjectServiceFeign;
import com.offcn.order.vo.req.OrderSubmitVo;
import com.offcn.order.vo.resp.TReturn;
import com.offcn.response.AppResponse;
import com.offcn.utils.AppDateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private ProjectServiceFeign projectServiceFeign;

    @Resource
    private TOrderMapper tOrderMapper;
    @Override
    public TOrder saveOrder(OrderSubmitVo vo) {
        TOrder tOrder = new TOrder();
        //通过令牌获取用户id
        String accessToken = vo.getAccessToken();
        String memberId = redisTemplate.opsForValue().get(accessToken);
        tOrder.setMemberid(Integer.parseInt(memberId));
        //将vo中的属性值复制给torder
        BeanUtils.copyProperties(vo,tOrder);
        //设定orderNum
        String orderNum = UUID.randomUUID().toString().replace("-", "");
        tOrder.setOrdernum(orderNum);
        //设定订单时间
        tOrder.setCreatedate(AppDateUtils.getFormatTime());
        //设定状态
        tOrder.setStatus(OrderStatusEnume.UNPAY.getCode()+"");
        //设定订单金额
        AppResponse<List<TReturn>> returnListResponse = projectServiceFeign.getReturnList(vo.getProjectId());
        //默认获取第一个
        List<TReturn> list = returnListResponse.getData();
        TReturn tReturn = list.get(0);
        //获取对应的回报价格和数量
        int money= tOrder.getRtncount() * tReturn.getSupportmoney() + tReturn.getFreight();
        tOrder.setMoney(money);
        tOrderMapper.insert(tOrder);
        return tOrder;

    }
}
