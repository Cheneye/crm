package com.cl.crm.dao;

import com.cl.base.BaseMapper;
import com.cl.crm.po.CustomerLinkman;
import org.apache.ibatis.annotations.Param;

public interface CustomerLinkmanMapper extends BaseMapper<CustomerLinkman,Integer> {

    CustomerLinkman queryByPhone(@Param("phone") String phone,@Param("cusId") Integer cusId);
}