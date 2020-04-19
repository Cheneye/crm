package com.cl.crm.dao;

import com.cl.base.BaseMapper;
import com.cl.crm.po.Customer;
import com.cl.crm.query.CustomerQuery;

import java.util.List;
import java.util.Map;

public interface CustomerMapper extends BaseMapper<Customer,Integer> {

    Customer queryCustomerByName(String name);
    List<Customer> queryCustomerLoss();
    Integer updateStateBatchByIds(List<Integer> cusIds);
    List<Map<String,Object>> queryCustomerContributionByParams(CustomerQuery customerQuery);
    List<Map<String,Object>> queryCustomerMake();
    List<Map<String,Object>> queryCustomerService();
}