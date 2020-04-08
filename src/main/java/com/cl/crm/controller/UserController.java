package com.cl.crm.controller;

import com.cl.base.BaseController;
import com.cl.crm.po.User;
import com.cl.crm.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
}
