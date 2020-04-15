package com.cl.crm.controller;

import com.cl.base.BaseController;
import com.cl.crm.query.OrderDetailsQuery;
import com.cl.crm.service.OrderDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping("order_details")
public class OrderDetailsController extends BaseController {

    @Resource
    private OrderDetailsService orderDetailsService;

    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> queryOrderDetails(OrderDetailsQuery orderDetailsQuery){
        return orderDetailsService.queryByParamsForDataGrid(orderDetailsQuery);
    }

}
