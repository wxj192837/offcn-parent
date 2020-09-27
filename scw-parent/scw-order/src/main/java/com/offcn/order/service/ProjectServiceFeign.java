package com.offcn.order.service;

import com.offcn.order.vo.resp.TReturn;
import com.offcn.response.AppResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value = "SCW-PROJECT",fallback = ProjectServiceFeign.class)
public interface ProjectServiceFeign {

    @GetMapping("/project/returns/{projectid}")
    public AppResponse<List<TReturn>> getReturnList(@PathVariable("projectid") Integer projectid);
}
