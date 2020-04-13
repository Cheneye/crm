package com.cl.crm.controller;

import com.cl.base.BaseController;
import com.cl.crm.annotaions.RequirePermission;
import com.cl.crm.exceptions.ParamsException;
import com.cl.crm.modal.ResultInfo;
import com.cl.crm.modal.UserModal;
import com.cl.crm.po.User;
import com.cl.crm.query.UserQuery;
import com.cl.crm.service.UserService;
import com.cl.crm.utils.LoginUserUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class UserController extends BaseController {

    @Resource
    private UserService userService;

    @GetMapping("user/selectByPrimaryKey")
    @ResponseBody
    public User selectByPrimaryKey(Integer userId){
       return userService.selectByPrimaryKey(userId);
    }

    @PostMapping("user/login")
    @ResponseBody
    public ResultInfo login(String userName,String userPwd){
        UserModal userModal = userService.login(userName, userPwd);
        return  success("用户登录成功！",userModal);
    }

    @PostMapping("user/updatePassword")
    @ResponseBody
    public ResultInfo updatePassword(HttpServletRequest request, String oldPassword, String newPassword, String confirmPassword){
        //通过cookie获取用户id
        int userId = LoginUserUtil.releaseUserIdFromCookie(request);
        userService.updatePassword(userId,oldPassword, newPassword,confirmPassword);
        return  success("用户密码修改成功！");
    }

    @RequestMapping("user/index")
    @RequirePermission(code = "6010")
    public String index(){
        return "user";
    }

    @RequestMapping("user/save")
    @ResponseBody
    @RequirePermission(code = "601001")
    public ResultInfo saveUser(User user){
        userService.saveUser(user);
        return success("用户添加成功！");
    }

    @RequestMapping("user/list")
    @ResponseBody
    @RequirePermission(code = "601002")
    public Map<String,Object> queryUsersByParams(UserQuery userQuery){
        return userService.queryByParamsForDataGrid(userQuery);
    }

    @RequestMapping("user/update")
    @ResponseBody
    @RequirePermission(code = "601003")
    public ResultInfo updateUser(User user){
        userService.updateUser(user);
        return success("用户更新成功！");
    }

    @RequestMapping("user/delete")
    @ResponseBody
    @RequirePermission(code = "601004")
    public ResultInfo deleteUser(@RequestParam(name = "id") Integer userId){
        userService.deleteUser(userId);
        return success("用户删除成功！");
    }

}
