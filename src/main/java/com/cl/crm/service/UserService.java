package com.cl.crm.service;

import com.cl.base.BaseService;
import com.cl.crm.dao.UserMapper;
import com.cl.crm.modal.ResultInfo;
import com.cl.crm.modal.UserModal;
import com.cl.crm.po.User;
import com.cl.crm.utils.AssertUtil;
import com.cl.crm.utils.Md5Util;
import com.cl.crm.utils.UserIDBase64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserService extends BaseService<User,Integer> {

    @Resource
    private UserMapper userMapper;

    /**
     * 用户登录
     * @param userName
     * @param userPwd
     * @return
     */
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
        return new UserModal(UserIDBase64.encoderUserID(user.getId()),user.getUserName(),user.getTrueName());
    }

    public void updatePassword(Integer userId,String oldPassword,String newPassword,String confirmPassword){
        /**
         * 参数校验：
         *      userId  非空  记录必须存在
         *      oldPassword  非空  必须与数据库一致
         *      newPassword 非空   新密码不能与原始密码相同
         *      confirmPassword 非空  与新密码必须一致
         */
        User user = selectByPrimaryKey(userId);
        checkParams(user,userId,oldPassword,newPassword,confirmPassword);
        user.setUserPwd(Md5Util.encode(newPassword));
        Integer row = updateByPrimaryKeySelective(user);
        AssertUtil.isTrue(row<1,"密码修改失败！");
    }

    private void checkParams(User user,Integer userId,String oldPassword,String newPassword,String confirmPassword) {

        AssertUtil.isTrue(null==userId||null==user,"用户未登录或用户不存在！");
        AssertUtil.isTrue(StringUtils.isBlank(oldPassword),"原密码不能为空！");
        AssertUtil.isTrue(StringUtils.isBlank(newPassword),"新密码不能为空！");
        AssertUtil.isTrue(StringUtils.isBlank(confirmPassword),"确定密码不能为空！");
        AssertUtil.isTrue(!((Md5Util.encode(oldPassword)).equals(user.getUserPwd())),"原密码错误！");
        AssertUtil.isTrue(newPassword.equals(oldPassword),"新密码不能与旧密码相同！");
        AssertUtil.isTrue(!(newPassword.equals(confirmPassword)),"新密码输入不一致！");
    }

}
