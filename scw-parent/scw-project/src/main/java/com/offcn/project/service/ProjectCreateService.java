package com.offcn.project.service;


import com.offcn.project.enums.ProjectStatusEnume;
import com.offcn.project.vo.req.ProjectRedisStorageVo;

public interface ProjectCreateService {

    public String initCreateProject(Integer memberid);

    //保存项目信息
    public void saveProjectInfo(ProjectStatusEnume status, ProjectRedisStorageVo redisVo);
}
