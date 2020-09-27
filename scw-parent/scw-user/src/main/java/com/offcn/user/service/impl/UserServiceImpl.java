package com.offcn.user.service.impl;

import com.offcn.user.enums.UserExceptionEnum;
import com.offcn.user.exception.UserException;
import com.offcn.user.mapper.TMemberAddressMapper;
import com.offcn.user.mapper.TMemberMapper;
import com.offcn.user.pojo.*;
import com.offcn.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

@Resource
private TMemberMapper memberMapper;
@Resource
private TMemberAddressMapper memberAddressMapper;
    @Override
    public void registerUser(TMember tMember) {
    //检查用户名(手机号)是否存在
        TMemberExample example = new TMemberExample();
        TMemberExample.Criteria criteria=example.createCriteria();
        criteria.andUsernameEqualTo(tMember.getUsername());
        long count=memberMapper.countByExample(example);
    if (count>0){
        throw new UserException(UserExceptionEnum.LOGIN_EXIST);
    }
        // 完成密码加密
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encode = encoder.encode(tMember.getUserpswd());
        tMember.setUserpswd(encode);

        tMember.setAuthstatus("0");
        tMember.setUsertype("0");
        tMember.setAccttype("2");  // 个人

        // 插入数据
        memberMapper.insert(tMember);
    }

    @Override
    public TMember login(String username, String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        TMemberExample example = new TMemberExample();
        example.createCriteria().andLoginacctEqualTo(username);
        List<TMember> list = memberMapper.selectByExample(example);
        if(list!=null && list.size()==1){
            TMember member = list.get(0);
            boolean matches = encoder.matches(password, member.getUserpswd());
            return matches?member:null;
        }
        return null;
    }

    @Override
    public TMember findTmemberById(Integer id) {
        return memberMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<TMemberAddress> findAddressList(Integer memberid) {
        TMemberAddressExample example = new TMemberAddressExample();
        TMemberAddressExample.Criteria criteria = example.createCriteria();
        criteria.andMemberidEqualTo(memberid);
        return memberAddressMapper.selectByExample(example);
    }
}
