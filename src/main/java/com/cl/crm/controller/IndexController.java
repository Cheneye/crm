package com.cl.crm.controller;

import com.cl.base.BaseController;
import com.cl.crm.exceptions.ParamsException;
import com.cl.crm.service.UserService;
import com.cl.crm.utils.LoginUserUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController extends BaseController {

    @Resource
    private UserService userService;

    @RequestMapping("/index")
    public String index(){
        return "index";
    }

    @RequestMapping("/main")
    public String main(HttpServletRequest request){
        int userId = LoginUserUtil.releaseUserIdFromCookie(request);
        request.setAttribute("user",userService.selectByPrimaryKey(userId));
        return "main";
    }
}
