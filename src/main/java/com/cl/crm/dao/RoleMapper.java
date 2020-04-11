package com.cl.crm.dao;

import com.cl.base.BaseMapper;
import com.cl.crm.po.Role;

import java.util.List;
import java.util.Map;

public interface RoleMapper extends BaseMapper<Role,Integer> {

    List<Map<String,Object>> queryAllRoles();
    Role queryRoleByRoleName(String RoleName);

}