package com.offcn.project.controller;

import com.alibaba.fastjson.JSON;
import com.offcn.project.enums.ProjectStatusEnume;
import com.offcn.project.pojo.*;
import com.offcn.project.service.ProjectCreateService;
import com.offcn.project.service.ProjectInfoService;
import com.offcn.project.utils.OssTemplate;
import com.offcn.project.vo.BaseVo;
import com.offcn.project.vo.req.ProjectBaseInfoVo;
import com.offcn.project.vo.req.ProjectRedisStorageVo;
import com.offcn.project.vo.req.ProjectReturnVo;
import com.offcn.project.vo.resp.ProjectDetailVo;
import com.offcn.project.vo.resp.ProjectVo;
import com.offcn.response.AppResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Api(tags="项目模块")
public class ProjectController {
    @Autowired
    private OssTemplate ossTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ProjectCreateService projectCreateService;

    @Autowired
    private ProjectInfoService projectInfoService;


    @PostMapping("/upload")
    public AppResponse<Map> upload(@RequestParam("file")MultipartFile[] files) throws IOException {
         Map<String,Object> map=new HashMap<>();
        List urls = new ArrayList();
        if (files != null && files.length>0){
          for (MultipartFile file:files){
              if (!file.isEmpty()){
                  String url = ossTemplate.upload(file.getInputStream(), file.getOriginalFilename());
                  urls.add(url);
              }
          }
         }
         map.put("urls",urls);
        return AppResponse.ok(map);
    }

    @ApiOperation(value="项目的基本模块")
    @PostMapping("/init")
    public AppResponse<String> init(BaseVo vo){
        //获取用户令牌
        String accessToken = vo.getAccessToken();
        //根据用户令牌获取用户id
        String memberId = (String) redisTemplate.opsForValue().get(accessToken);
       if (memberId==null || memberId.length()==0){
           return AppResponse.fail("无权限创建项目");

       }

        int userId = Integer.parseInt(memberId);
        String projectToken = projectCreateService.initCreateProject(userId);
        return AppResponse.ok(projectToken);
    }

    @ApiOperation(value="项目的基本信息保存")
    @PostMapping("/saveBaseInfo")
    public AppResponse<String> saveBaseInfo(ProjectBaseInfoVo infoVo){
        // 根据项目的临时令牌获取项目信息
        String jsonProject = (String) redisTemplate.opsForValue().get(infoVo.getProjectToken());
        // 将json字符串转为对象
        ProjectRedisStorageVo redisVo = JSON.parseObject(jsonProject, ProjectRedisStorageVo.class);
        // 将 infoVo 用户提交的 数据存储到redisVo中
        BeanUtils.copyProperties(infoVo,redisVo);
        // 再将redisVo的数据存储会redis中
        jsonProject = JSON.toJSONString(redisVo);
        redisTemplate.opsForValue().set(infoVo.getProjectToken(),jsonProject);
        return AppResponse.ok("OK");
    }
    @ApiOperation(value="项目的回报信息保存")
    @PostMapping("/saveReturnInfo")
    public AppResponse<String> saveReturnInfo(@RequestBody List<ProjectReturnVo> list){
        ProjectReturnVo projectReturnVo = list.get(0);
        String projectToken = projectReturnVo.getProjectToken();
        String projectJson = (String)redisTemplate.opsForValue().get(projectToken);
        ProjectRedisStorageVo redisVo = JSON.parseObject(projectJson, ProjectRedisStorageVo.class);
        List<TReturn> returns = new ArrayList<>();
        for (ProjectReturnVo returnVo:list){
            TReturn tReturn = new TReturn();
            BeanUtils.copyProperties(returnVo,tReturn);
            returns.add(tReturn);
        }
        //将returnS数据加入到Redisvo中
        redisVo.setProjectReturns(returns);
        //将惹Redisvo写入到Redis
     projectJson= JSON.toJSONString(redisVo);
     redisTemplate.opsForValue().set(projectToken,projectJson);
     return AppResponse.ok("ok");
    }
    @ApiOperation(value = "保存项目所有信息到数据库")
    @PostMapping("/submit")
    public AppResponse<String> submit(String accessToken,String projectToken){
        // 检查accessToken
        String memberId = (String) redisTemplate.opsForValue().get(accessToken);
        if(memberId == null || memberId.length() == 0){
            return AppResponse.fail("无权限处理");
        }
        // 获取项目在redis中的存储的信息
        String projectJson = (String) redisTemplate.opsForValue().get(projectToken);
        if(projectJson == null || projectJson.length() == 0){
            return AppResponse.fail("数据为空");
        }
        ProjectRedisStorageVo redisVo = JSON.parseObject(projectJson, ProjectRedisStorageVo.class);
        projectCreateService.saveProjectInfo(ProjectStatusEnume.SUBMIT_AUTH,redisVo);
        return AppResponse.ok("提交成功");
    }

    @ApiOperation(value="获取项目回报列表")
    @GetMapping("/project/returns/{projectid}")
    public AppResponse<List<TReturn>> getReturnList(@PathVariable("projectid") Integer projectid){
        List<TReturn> returnList = projectInfoService.getReturnList(projectid);
        return AppResponse.ok(returnList);
    }


    @ApiOperation(value="获取所有项目")
    @GetMapping("/all")
    public AppResponse<List<ProjectVo>> findAllProject(){
        List<TProject> allProject = projectInfoService.findAllProject();
        //定义一个list用于返回
        ArrayList<ProjectVo> list = new ArrayList<>();
        //遍历
        for (TProject pro: allProject){
            //获取项目id
            Integer proId = pro.getId();
            //创建projectVo对象
            ProjectVo projectVo = new ProjectVo();
            BeanUtils.copyProperties(pro,projectVo);
            //根据id获取那图片
            List<TProjectImages> projectImages = projectInfoService.getProjectImages(proId);
if (projectImages!=null){

    for (TProjectImages iamge:projectImages){
        if (iamge.getImgtype().equals(0)){
            projectVo.setHeaderImage(iamge.getImgurl());
        }
    }
}
            list.add(projectVo);
        }
        return AppResponse.ok(list);
    }

    @ApiOperation(value = "获取项目详细信息")
    @GetMapping("/findProjectInfo")
    public AppResponse<ProjectDetailVo> findProjectInfo(Integer projectID){
        ProjectDetailVo detailVo = new ProjectDetailVo();
        // 查询所有的图片
        List<TProjectImages> images = projectInfoService.getProjectImages(projectID);
        List<String> detailsImages = detailVo.getDetailsImages();
        if(detailsImages == null){
            detailsImages = new ArrayList<>();
        }
        if(images != null) {
            for (TProjectImages image : images) {
                // 头图片
                if (image.getImgtype().equals("0")) {
                    detailVo.setHeaderImage(image.getImgurl());
                } else { // 详情图片
                    detailsImages.add(image.getImgurl());
                }
            }
        }
        // 设置详情图片
        detailVo.setDetailsImages(detailsImages);
        // 回报
        List<TReturn> returnList = projectInfoService.getReturnList(projectID);
        detailVo.setProjectReturns(returnList);
        // 其他属性
        TProject projectFormSQL = projectInfoService.findProjectById(projectID);
        BeanUtils.copyProperties(projectFormSQL,detailVo);
        return AppResponse.ok(detailVo);
    }

    @ApiOperation(value = "获取项目回报")
    @GetMapping("/returns/getOne/{returnId}")
    public AppResponse<TReturn> findReturnById(@PathVariable("returnId") Integer returnID){
        TReturn aReturn = projectInfoService.findReturn(returnID);
        return AppResponse.ok(aReturn);
    }

    @ApiOperation(value = "获取项目分类")
    @GetMapping("/allType")
    public AppResponse<List<TType>> findAllType(){
        List<TType> allTType = projectInfoService.findAllTType();
        return AppResponse.ok(allTType);
    }

    @ApiOperation(value = "获取所有标签")
    @GetMapping("/findAllTag")
    public AppResponse<List<TTag>> findAllTag(){
        List<TTag> allAllTag = projectInfoService.findAllTag();
        return AppResponse.ok(allAllTag);
    }

}
