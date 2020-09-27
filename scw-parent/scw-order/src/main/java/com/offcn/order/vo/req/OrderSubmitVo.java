package com.offcn.order.vo.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderSubmitVo {
    // 用户令牌
    private String accessToken;

    private Integer projectId; // 项目ID
    private Integer returnId; // 回报ID
    private Integer rtncount; //回报数量
    private String address;  // 收货地址
    private String invoice;  // 是否要开发票
    private String invoictitle; // 发票的抬头
    private String remark;   // 备注
}
