package com.cl.crm.service;

import com.cl.base.BaseService;
import com.cl.crm.dao.ModuleMapper;
import com.cl.crm.dao.PermissionMapper;
import com.cl.crm.dao.RoleMapper;
import com.cl.crm.po.Permission;
import com.cl.crm.po.Role;
import com.cl.crm.utils.AssertUtil;
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
public class RoleService extends BaseService<Role,Integer> {

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private PermissionMapper permissionMapper;

    @Resource
    private ModuleMapper moduleMapper;

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

    @Transactional(propagation = Propagation.REQUIRED)
    public void addGrant(Integer roleId,Integer[] mids){
        AssertUtil.isTrue(null==roleId || null==selectByPrimaryKey(roleId),"授权用户不存在！" );
        Integer count = permissionMapper.countPermissionByRoleId(roleId);
        if(count>0){
            AssertUtil.isTrue(permissionMapper.deletePermissionsByRoleId(roleId)<1,"权限分配失败！");
        }
        if(null!=mids && mids.length>0){
            List<Permission> permissions =new ArrayList<>();
            for (Integer mid : mids){
                Permission permission = new Permission();
                permission.setRoleId(roleId);
                permission.setModuleId(mid);
                permission.setCreateDate(new Date());
                permission.setUpdateDate(new Date());
                permission.setAclValue(moduleMapper.selectByPrimaryKey(mid).getOptValue());
                permissions.add(permission);
            }
            AssertUtil.isTrue(permissionMapper.insertBatch(permissions)<mids.length,"授权失败！");
        }
    }

}
