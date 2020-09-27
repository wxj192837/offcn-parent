package com.offcn.user.service;


import com.offcn.user.pojo.TMember;
import com.offcn.user.pojo.TMemberAddress;

import java.util.List;

public interface UserService {
public void registerUser(TMember tMember);

    public TMember login(String username,String password);

    //根据用户id，获取用户信息
    public TMember findTmemberById(Integer id);

    //获取用户地址
    List<TMemberAddress> findAddressList(Integer memberid);
}
