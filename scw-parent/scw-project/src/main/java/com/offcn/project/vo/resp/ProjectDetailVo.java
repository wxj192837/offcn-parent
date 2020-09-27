package com.offcn.project.vo.resp;


import com.offcn.project.pojo.TReturn;
import lombok.Data;

import java.util.List;
@Data
public class ProjectDetailVo {


    private Integer id;

    private String name;

    private String remark;

    private Long money;

    private Integer day;

    private String status;

    private String deploydate;

    private Long supportmoney;

    private Integer supporter;

    private Integer completion;

    private Integer memberid;

    private String createdate;

    private Integer follower;

    // 头图片
    private String headerImage;

    // 详情图片
    private List<String> detailsImages;

    // 回报
    private List<TReturn> projectReturns;
}
