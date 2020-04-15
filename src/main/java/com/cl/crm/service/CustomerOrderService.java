package com.cl.crm.service;

import com.cl.base.BaseService;
import com.cl.crm.dao.CustomerOrderMapper;
import com.cl.crm.po.CustomerOrder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class CustomerOrderService extends BaseService<CustomerOrder,Integer> {

    @Resource
    private CustomerOrderMapper customerOrderMapper;

    public Map<String,Object> queryOrderDetailByOrderId(Integer orderId){
        return  customerOrderMapper.queryOrderDetailByOrderId(orderId);
    }
}
