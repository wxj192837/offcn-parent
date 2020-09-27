package com.offcn.order.service.impl;


import com.offcn.order.service.ProjectServiceFeign;
import com.offcn.order.vo.resp.TReturn;
import com.offcn.response.AppResponse;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class ProjectSeviceFeignImpl implements ProjectServiceFeign {
    @Override
    public AppResponse<List<TReturn>> getReturnList(Integer projectid) {
        AppResponse<List<TReturn>> fail = AppResponse.fail(null);
        fail.setMsg("远程调用失败");
        return fail;
    }
}
