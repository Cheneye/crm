package com.cl.crm.controller;

import com.cl.base.BaseController;

import com.cl.crm.query.CustomerOrderQuery;
import com.cl.crm.service.CustomerOrderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping("customer_order")
public class CustomerOrderController extends BaseController {

    @Resource
    private CustomerOrderService customerOrderService;

    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> index(CustomerOrderQuery customerOrderQuery){
        return customerOrderService.queryByParamsForDataGrid(customerOrderQuery);
    }

    @RequestMapping("queryOrderInfo")
    @ResponseBody
    public Map<String,Object> queryOrderInfo(Integer orderId){
        return customerOrderService.queryOrderDetailByOrderId(orderId);
    }

}
