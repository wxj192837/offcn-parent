package com.offcn.project.vo.req;


import com.offcn.project.pojo.TReturn;
import com.offcn.project.vo.BaseVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
@Data
@ApiModel
public class ProjectBaseInfoVo extends BaseVo {

    // 项目的临时令牌
    @ApiModelProperty("项目的临时令牌")
    private String projectToken;
    // 项目名
    @ApiModelProperty("项目名")
    private String name;
    // 项目简介
    @ApiModelProperty("项目简介")
    private String remark;
    //  金额
    @ApiModelProperty("金额")
    private Long money;
    // 天数
    @ApiModelProperty("天数")
    private Integer day;

    // 头部图片
    @ApiModelProperty("头部图片")
    private String headerImage;
    // 详情图片
    @ApiModelProperty("详情图片")
    private List<String> detailsImages;

    @ApiModelProperty("类别")
    private List<Integer> typeids; // 类别
    @ApiModelProperty("标签")
    private List<Integer> tagids; // 标签
}
