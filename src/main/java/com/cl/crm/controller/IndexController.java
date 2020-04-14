package com.cl.crm.controller;

import com.cl.base.BaseController;
import com.cl.crm.dto.ModuleDto;
import com.cl.crm.service.ModuleService;
import com.cl.crm.service.PermissionService;
import com.cl.crm.service.UserService;
import com.cl.crm.utils.LoginUserUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController extends BaseController {

    @Resource
    private UserService userService;

    @Resource
    private PermissionService permissionService;

    @Resource
    private ModuleService moduleService;

    @RequestMapping("/index")
    public String index(){
        return "index";
    }

    @RequestMapping("/main")
    public String main(HttpServletRequest request){

        int userId = LoginUserUtil.releaseUserIdFromCookie(request);
        request.setAttribute("user",userService.selectByPrimaryKey(userId));

        List<String> permissions = permissionService.queryUserHasRolesHasPermissions(userId);
        request.getSession().setAttribute("permissions",permissions);

        List<ModuleDto> moduleDtos = moduleService.queryUserHasRoleHasModuleDtos(userId);
        request.getSession().setAttribute("modules",moduleDtos);

        return "main_02";
    }
}
