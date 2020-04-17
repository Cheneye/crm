package com.cl.crm.service;

import com.cl.base.BaseService;
import com.cl.crm.dao.UserMapper;
import com.cl.crm.dao.UserRoleMapper;
import com.cl.crm.modal.UserModal;
import com.cl.crm.po.User;
import com.cl.crm.po.UserRole;
import com.cl.crm.utils.AssertUtil;
import com.cl.crm.utils.Md5Util;
import com.cl.crm.utils.PhoneUtil;
import com.cl.crm.utils.UserIDBase64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class UserService extends BaseService<User,Integer> {

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserRoleMapper userRoleMapper;
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

    @Transactional(propagation = Propagation.REQUIRED)
    public void saveUser(User user){
        /**
         * 1.参数校验
         *     用户名  非空   唯一
         *     email  非空  格式合法
         *     手机号 非空  格式合法
         * 2.设置默认参数
         *      isValid 1
         *      createDate   uddateDate
         *      userPwd   123456->md5加密
         * 3.执行添加  判断结果
         */
        checkParams(user.getUserName(), user.getEmail(), user.getPhone());
        User temp = userMapper.queryByUserName(user.getUserName());
        AssertUtil.isTrue(null != temp && (temp.getIsValid() == 1), "该用户已存在!");
        user.setIsValid(1);
        user.setCreateDate(new Date());
        user.setUpdateDate(new Date());
        user.setUserPwd(Md5Util.encode("123456"));
        AssertUtil.isTrue(insertHasKey(user) == null, "用户添加失败!");
        int userId = user.getId();
        relaionUserRole(userId,user.getRoleIds());
    }

    private void relaionUserRole(int userId, List<Integer> roleIds) {
        /**
         * 用户角色分配
         *   原始角色不存在   添加新的角色记录
         *   原始角色存在     添加新的角色记录
         *   原始角色存在     清空所有角色
         *   原始角色存在     移除部分角色
         * 如何进行角色分配???
         *  如果用户原始角色存在  首先清空原始所有角色
         *  添加新的角色记录到用户角色表
         */
        Integer count = userRoleMapper.countUserRoleByUserId(userId);
        if(count>0){
            AssertUtil.isTrue(userRoleMapper.deleteUserRoleByUserId(userId)!=count,"用户角色分配失败！");
        }
        if (null != roleIds && roleIds.size() > 0) {
            List<UserRole> userRoles = new ArrayList<UserRole>();
            roleIds.forEach(roleId -> {
                UserRole userRole = new UserRole();
                userRole.setUserId(userId);
                userRole.setRoleId(roleId);
                userRole.setCreateDate(new Date());
                userRole.setUpdateDate(new Date());
                userRoles.add(userRole);
            });
            AssertUtil.isTrue(userRoleMapper.insertBatch(userRoles) < userRoles.size(), "用户角色分配失败！");
        }


    }

    private void checkParams(String userName, String email, String phone) {
        AssertUtil.isTrue(StringUtils.isBlank(userName),"用户名不能为空！");
        AssertUtil.isTrue(StringUtils.isBlank(email), "请输入邮箱地址！");
        AssertUtil.isTrue(!(PhoneUtil.isMobile(phone)),"手机号格式不合法！");
    }

    public void updateUser(User user){
        /**
         * 1.参数校验
         *     id 非空  记录必须存在
         *     用户名  非空   唯一
         *     email  非空  格式合法
         *     手机号 非空  格式合法
         * 2.设置默认参数
         *        uddateDate
         * 3.执行更新  判断结果
         */

        AssertUtil.isTrue(null==user.getId(),"待更新用户不存在！");
        AssertUtil.isTrue(null==selectByPrimaryKey(user.getId()),"待更新用户不存在！");
        checkParams(user.getUserName(),user.getEmail(),user.getPhone());
        User temp = userMapper.queryByUserName(user.getUserName());
        if (null != temp && temp.getIsValid() == 1) {
            //去除查询的自身
            AssertUtil.isTrue(!(user.getId().equals(temp.getId())),"该用户已存在！");
        }
        user.setUpdateDate(new Date());
        AssertUtil.isTrue(updateByPrimaryKeySelective(user)<1,"用户更新失败！");
        relaionUserRole(user.getId(),user.getRoleIds());
    }

    public void deleteUser(Integer userId){
        User temp = selectByPrimaryKey(userId);
        AssertUtil.isTrue(null==userId||null==temp,"待删除用户不存在！");
        Integer count = userRoleMapper.countUserRoleByUserId(userId);
        if(count>0){
            AssertUtil.isTrue(userRoleMapper.deleteUserRoleByUserId(userId)!=count,"用户角色分配删除失败！");
        }
        temp.setIsValid(0);
        AssertUtil.isTrue(updateByPrimaryKeySelective(temp)<1,"用户删除失败！");

    }

    public List<Map<String,Object>> queryAllCustomerManager() {
        return userMapper.queryAllCustomerManager();
    }
}
