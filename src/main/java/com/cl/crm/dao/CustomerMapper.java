package com.cl.crm.dao;

import com.cl.base.BaseMapper;
import com.cl.crm.po.Customer;

import java.util.List;

public interface CustomerMapper extends BaseMapper<Customer,Integer> {

    Customer queryCustomerByName(String name);
    List<Customer> queryCustomerLoss();
    Integer updateStateBatchByIds(List<Integer> cusIds);
}