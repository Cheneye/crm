package com.cl.crm.dao;

import com.cl.base.BaseMapper;
import com.cl.crm.po.User;

import java.util.List;
import java.util.Map;

public interface UserMapper extends BaseMapper<User,Integer> {


    User queryByUserName(String userName);

    List<Map<String, Object>> queryAllCustomerManager();
}