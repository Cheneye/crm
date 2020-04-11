package com.cl.crm.dao;

import com.cl.base.BaseMapper;
import com.cl.crm.po.UserRole;

public interface UserRoleMapper extends BaseMapper<UserRole, Integer> {
    Integer countUserRoleByUserId(Integer userId);
    Integer deleteUserRoleByUserId(Integer userId);
}