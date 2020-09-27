package com.offcn.project.vo.req;

//在Redis当中存储项目的内容

import com.offcn.project.pojo.TReturn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectRedisStorageVo {
//定义项目临时的token
    private String projectToken;
   private Integer memberid;
   private List<Integer> typeids;
   private List<Integer> tagids;
   private List<TReturn> projectReturns;    
   private String headerImage;
   private List<String> detailsImages;
    private String name;
    private Integer day;
    private Long money;
    private String remark;
}
