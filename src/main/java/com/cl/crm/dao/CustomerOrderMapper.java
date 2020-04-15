package com.cl.crm.dao;

import com.cl.base.BaseMapper;
import com.cl.crm.po.CustomerOrder;

import java.util.Map;

public interface CustomerOrderMapper extends BaseMapper<CustomerOrder,Integer> {

    Map<String, Object> queryOrderDetailByOrderId(Integer orderId);
}