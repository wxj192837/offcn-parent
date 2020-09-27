package com.offcn.project.service;


import com.offcn.project.pojo.*;

import java.util.List;

public interface ProjectInfoService {
  List<TReturn> getReturnList(Integer projectId);

    //获取所有项目
  List<TProject> findAllProject();
    //获取所有项目
    List<TProjectImages> getProjectImages(Integer id);
    TProject findProjectById(Integer projectId);

    List<TTag> findAllTag();

    List<TType> findAllTType();

    TReturn findReturn(Integer returnId);
}
