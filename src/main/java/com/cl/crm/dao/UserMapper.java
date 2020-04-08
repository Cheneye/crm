package com.cl.crm.dao;

import com.cl.base.BaseMapper;
import com.cl.crm.po.User;

public interface UserMapper extends BaseMapper<User,Integer> {


    User queryByUserName(String userName);
}