package com.offcn.user.controller;

import com.offcn.response.AppResponse;
import com.offcn.user.component.SmsTemplate;
import com.offcn.user.pojo.TMember;
import com.offcn.user.service.UserService;
import com.offcn.user.vo.req.UserRegistVo;
import com.offcn.user.vo.resp.UserRespVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
@Api(tags= "用户获取验证码,登录,注册")
public class UserLoginController {

    @Autowired
    private SmsTemplate smsTemplate;
     @Autowired
     private UserService userService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @ApiOperation("发送短信验证码")
    @PostMapping("/sendSms")
    public AppResponse<Object> sendSms(String phoneNum){
        String code = UUID.randomUUID().toString().substring(0, 4);

        stringRedisTemplate.opsForValue().set(phoneNum,code);
        HashMap<String,String> map=new HashMap<>();

        map.put("mobile",phoneNum);
        map.put("param","code:"+code);
        String sendCode = smsTemplate.sendSms(map);
        if ("fail".equalsIgnoreCase(sendCode)){
            return AppResponse.fail("短信发送失败");
        }
return AppResponse.ok("短信发送成功");
    }

    @ApiOperation("注册功能")
    @PostMapping("/register")
    public AppResponse<Object> register(UserRegistVo user, String userCode){
        //校验手机号对应的验证码
        String code=stringRedisTemplate.opsForValue().get(user.getLoginnacct());
        if (user.getCode()!=null && user.getCode().length() >0 && code.equals(user.getCode())){
            TMember tMember = new TMember();
            BeanUtils.copyProperties(user,tMember);
            userService.registerUser(tMember);
            return AppResponse.ok("注册成功");
        }else{
            return AppResponse.fail("验证码错误");
        }


    }
    @ApiOperation("用户登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", required = true),
            @ApiImplicitParam(name = "password", value = "密码", required = true)
    })//@ApiImplicitParams：描述所有参数；@ApiImplicitParam描述某个参数
    @PostMapping("/login")
    public AppResponse<UserRespVo> login(String username, String password) {
        //1、尝试登录
        TMember member = userService.login(username, password);
        if (member == null) {
            //登录失败
            AppResponse<UserRespVo> fail = AppResponse.fail(null);
            fail.setMsg("用户名密码错误");
            return fail;
        }
        //2、登录成功;生成令牌
        String token = UUID.randomUUID().toString().replace("-", "");
        UserRespVo vo = new UserRespVo();
        BeanUtils.copyProperties(member, vo);
        vo.setAccessToken(token);

        //3、经常根据令牌查询用户的id信息
        stringRedisTemplate.opsForValue().set(token,member.getId()+"",2,TimeUnit.HOURS);
        return AppResponse.ok(vo);
    }

    //根据用户编号获取用户信息
    @GetMapping("/findUser/{id}")
    public AppResponse<UserRespVo> findUser(@PathVariable("id") Integer id){
        TMember tmember = userService.findTmemberById(id);
        UserRespVo userRespVo = new UserRespVo();
        BeanUtils.copyProperties(tmember,userRespVo);

        return AppResponse.ok(userRespVo);
    }
}
