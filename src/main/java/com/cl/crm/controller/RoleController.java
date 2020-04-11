package com.cl.crm.controller;

import com.cl.base.BaseController;
import com.cl.crm.modal.ResultInfo;
import com.cl.crm.po.Role;
import com.cl.crm.query.RoleQuery;
import com.cl.crm.service.RoleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("role")
public class RoleController extends BaseController {

    @Resource
    private RoleService roleService;

    @RequestMapping("queryAllRoles")
    @ResponseBody
    public List<Map<String,Object>> queryAllRoles(){
        return roleService.queryAllRoles();
    }

    @RequestMapping("index")
    public String index(){
        return "role";
    }

    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> queryRoleByParams(RoleQuery roleQuery){
        return roleService.queryByParamsForDataGrid(roleQuery);
    }

    @RequestMapping("save")
    @ResponseBody
    public ResultInfo saveUser(Role role){
        roleService.saveRole(role);
        return success("角色添加成功！");
    }
    @RequestMapping("update")
    @ResponseBody
    public ResultInfo updateUser(Role role){
        roleService.updateRole(role);
        return success("角色更新成功！");
    }

    @RequestMapping("delete")
    @ResponseBody
    public ResultInfo deleteUser(Integer id){
        roleService.deleteRole(id);
        return success("角色删除成功！");
    }


}
