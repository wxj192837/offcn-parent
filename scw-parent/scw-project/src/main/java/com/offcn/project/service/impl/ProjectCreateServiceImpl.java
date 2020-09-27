package com.offcn.project.service.impl;

import com.alibaba.fastjson.JSON;
import com.offcn.project.contants.ProjectContant;
import com.offcn.project.enums.ProjectImageTypeEnume;
import com.offcn.project.enums.ProjectStatusEnume;
import com.offcn.project.mapper.*;
import com.offcn.project.pojo.*;
import com.offcn.project.service.ProjectCreateService;
import com.offcn.project.vo.req.ProjectRedisStorageVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ProjectCreateServiceImpl implements ProjectCreateService {
@Autowired
private StringRedisTemplate stringRedisTemplate;

@Resource
private TProjectMapper projectMapper;
@Resource
private TProjectImagesMapper projectImagesMapper;
@Resource
private TProjectTypeMapper projectTypeMapper;
@Resource
private TProjectTagMapper projectTagMapper;
    @Resource
    private TReturnMapper returnMapper;


    @Override
    public String initCreateProject(Integer memberId) {
        // 定义项目的临时令牌
        String token = UUID.randomUUID().toString().replace("-", "");

        ProjectRedisStorageVo redisVo = new ProjectRedisStorageVo();
        // 用户id
        redisVo.setMemberid(memberId);
        // 项目令牌
        token = ProjectContant.TEMP_PROJECT + token;
        redisVo.setProjectToken(token);

        // 将redisVo转换为字符串
        String jsonString = JSON.toJSONString(redisVo);

        stringRedisTemplate.opsForValue().set(token,jsonString);

        return token;
    }

    @Override
    public void saveProjectInfo(ProjectStatusEnume status, ProjectRedisStorageVo redisVo) {
        TProject project = new TProject();
        BeanUtils.copyProperties(redisVo,project);
        //创建项目需要处理的字段
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        project.setCreatedate(dateFormat.format(new Date()));
        project.setStatus(String.valueOf(status.getCode()));
        //插入tProject的数据
        projectMapper.insert(project);
        //获取插入的id值
        Integer projectId = project.getId();
        //准备image中的数据
        String headerImage = redisVo.getHeaderImage();
        TProjectImages headImage = new TProjectImages(null, projectId, headerImage, ProjectImageTypeEnume.HEADER.getCode());
        projectImagesMapper.insert(headImage);
        //详情图片
        List<String> images = redisVo.getDetailsImages();
        if(images != null && images.size() > 0) {
            for (String img : images) {
                TProjectImages detailsImg = new TProjectImages(null, projectId, img, ProjectImageTypeEnume.DETAILS.getCode());
                projectImagesMapper.insert(detailsImg);
            }
        }
        // 标签
        List<Integer> tagids = redisVo.getTagids();
        for(Integer tagid : tagids){
            TProjectTag tag = new TProjectTag(null, projectId, tagid);
            projectTagMapper.insert(tag);
        }
        // 分类
        List<Integer> typeids = redisVo.getTypeids();
        for(Integer typeid : typeids){
            TProjectType type = new TProjectType(null, projectId, typeid);
            projectTypeMapper.insert(type);
        }
        // 回报
        List<TReturn> projectReturns = redisVo.getProjectReturns();
        for(TReturn tReturn : projectReturns){
            tReturn.setProjectid(projectId);
            returnMapper.insert(tReturn);
        }
        // 删除redis中的数据
        //stringRedisTemplate.delete(redisVo.getProjectToken());
    }
    }

