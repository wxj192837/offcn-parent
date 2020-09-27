package com.offcn.project.vo.req;

import com.offcn.project.vo.BaseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectReturnVo extends BaseVo {

    private String projectToken;

    @ApiModelProperty(value="回报类型：0 - 实物回报， 1 虚拟物品回报",required = true)
    private String type;
    @ApiModelProperty(value="支持金额",required = true)
    private Integer supportmoney;
    @ApiModelProperty(value="回报内容",required = true)
    private String content;
    @ApiModelProperty(value="回报的数量",required = true)
    private Integer count;
    @ApiModelProperty(value="单笔限购",required = true)
    private Integer signalpurchase;
    @ApiModelProperty(value="限购数量",required = true)
    private Integer purchase;
    @ApiModelProperty(value="运费",required = true)
    private Integer freight;
    @ApiModelProperty(value = "是否开发票: 0 - 不开发票， 1 - 开发票",required = true)
    private String invoice;
    @ApiModelProperty(value = "回报时间,众筹成功后多少天进行回报")
    private Integer rtndate;
}
