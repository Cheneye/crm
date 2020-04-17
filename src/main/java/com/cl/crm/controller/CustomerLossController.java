package com.cl.crm.controller;

import com.cl.base.BaseController;
import com.cl.crm.modal.ResultInfo;
import com.cl.crm.query.CustomerLossQuery;
import com.cl.crm.service.CustomerLossService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping("customer_loss")
public class CustomerLossController extends BaseController {

    @Resource
    private CustomerLossService customerLossService;

    @RequestMapping("index")
    public String index(){
        return "customer_loss";
    }

    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> queryCustomerLoss(CustomerLossQuery customerLossQuery){
        return customerLossService.queryByParamsForDataGrid(customerLossQuery);
    }

    @RequestMapping("confirmLoss")
    @ResponseBody
    public ResultInfo updateState(String lossReason,Integer lossId){
        customerLossService.updateState(lossReason,lossId);
        return success("流失成功！");
    }

}
