package com.cl.crm.service;

import com.cl.base.BaseService;
import com.cl.crm.dao.RoleMapper;
import com.cl.crm.po.Role;
import com.cl.crm.utils.AssertUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class RoleService extends BaseService<Role,Integer> {

    @Resource
    private RoleMapper roleMapper;

    public List<Map<String,Object>> queryAllRoles(){
       return roleMapper.queryAllRoles();
    }

    public void saveRole(Role role) {
        AssertUtil.isTrue(StringUtils.isBlank(role.getRoleName()),"角色不能为空！");
        Role temp = roleMapper.queryRoleByRoleName(role.getRoleName());
        AssertUtil.isTrue(temp!=null,"该角色已存在！");
        role.setCreateDate(new Date());
        role.setUpdateDate(new Date());
        role.setIsValid(1);
        AssertUtil.isTrue(insertSelective(role)<1,"角色添加失败！");
    }

    public void updateRole(Role role) {
        AssertUtil.isTrue(null==role.getId()||null==selectByPrimaryKey(role.getId()),"待更新角色记录不存在！");
        AssertUtil.isTrue(StringUtils.isBlank(role.getRoleName()),"角色名不能为空！");
        Role temp = roleMapper.queryRoleByRoleName(role.getRoleName());
        AssertUtil.isTrue(!(temp.getId().equals(role.getId())) && null!=temp,"该角色已存在！");
        role.setUpdateDate(new Date());
        AssertUtil.isTrue(updateByPrimaryKeySelective(role)<1,"角色更新失败！");

    }

    public void deleteRole(Integer id) {
        Role temp = selectByPrimaryKey(id);
        AssertUtil.isTrue(null==id || null==temp,"待删除的角色记录不存在");
        temp.setIsValid(0);
        temp.setUpdateDate(new Date());
        AssertUtil.isTrue(updateByPrimaryKeySelective(temp)<1,"角色删除失败！");
    }
}
