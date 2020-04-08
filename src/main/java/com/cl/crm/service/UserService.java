package com.cl.crm.service;

import com.cl.base.BaseService;
import com.cl.crm.dao.UserMapper;
import com.cl.crm.modal.ResultInfo;
import com.cl.crm.modal.UserModal;
import com.cl.crm.po.User;
import com.cl.crm.utils.AssertUtil;
import com.cl.crm.utils.Md5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserService extends BaseService<User,Integer> {

    @Resource
    private UserMapper userMapper;

    public UserModal login(String userName, String userPwd){
        //参数校验
        AssertUtil.isTrue(StringUtils.isBlank(userName),"用户名不能为空！");
        AssertUtil.isTrue(StringUtils.isBlank(userPwd),"用户密码不能为空！");
        //通过用户名判断用户是否存在
        User user = userMapper.queryByUserName(userName);
        AssertUtil.isTrue(null==user,"用户名已注销或不存在！");
        //判断密码是否正确
        AssertUtil.isTrue(!(user.getUserPwd().equals(Md5Util.encode(userPwd))),"用户密码错误！");
        return buildUserModelInfo(user);
    }

    private UserModal buildUserModelInfo(User user) {
        return new UserModal(Integer.toString(user.getId()),user.getUserName(),user.getTrueName());
    }
}
