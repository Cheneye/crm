package com.cl.crm.dao;

import com.cl.base.BaseMapper;
import com.cl.crm.po.Permission;

import java.util.List;

public interface PermissionMapper extends BaseMapper<Permission,Integer> {

    Integer countPermissionByRoleId(Integer roleId);
    Integer deletePermissionsByRoleId(Integer roleId);
    List<Integer> queryRoleHasAllModuleIdsByRoleId(Integer roleId);

    List<String> queryUserHasRolesHasPermissions(Integer userId);

    int countPermissionsByModuleId(Integer mid);
    int deletePermissionsByModuleId(Integer mid);
}