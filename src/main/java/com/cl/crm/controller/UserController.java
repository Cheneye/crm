package com.cl.crm.controller;

import com.cl.base.BaseController;
import com.cl.crm.exceptions.ParamsException;
import com.cl.crm.modal.ResultInfo;
import com.cl.crm.modal.UserModal;
import com.cl.crm.po.User;
import com.cl.crm.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class UserController extends BaseController {

    @Resource
    private UserService userService;

    @GetMapping("/user/selectByPrimaryKey")
    @ResponseBody
    public User selectByPrimaryKey(Integer userId){
       return userService.selectByPrimaryKey(userId);
    }

    @PostMapping("/user/login")
    @ResponseBody
    public ResultInfo login(String userName,String userPwd){
        ResultInfo resultInfo = new ResultInfo();
        try {
            UserModal userModal = userService.login(userName, userPwd);
            resultInfo.setResult(userModal);
        } catch (ParamsException e) {
            e.printStackTrace();
            resultInfo.setCode(e.getCode());
            resultInfo.setMsg(e.getMsg());
        }catch (Exception e) {
            e.printStackTrace();
            resultInfo.setCode(500);
            resultInfo.setMsg("failed");
        }
        return  resultInfo;
    }
}
