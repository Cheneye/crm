package com.cl.crm.controller;

import com.cl.base.BaseController;
import com.cl.crm.modal.ResultInfo;
import com.cl.crm.po.CustomerLoss;
import com.cl.crm.po.CustomerReprieve;
import com.cl.crm.query.CustomerReprieveQuery;
import com.cl.crm.service.CustomerLossService;
import com.cl.crm.service.CustomerReprieveService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping("customer_reprieve")
public class CustomerReprieveController extends BaseController {

    @Resource
    private CustomerLossService customerLossService;
    @Resource
    private CustomerReprieveService customerReprieveService;

    @RequestMapping("index")
    public String index(Model model,Integer lossId){
        CustomerLoss customerLoss = customerLossService.selectByPrimaryKey(lossId);
        model.addAttribute("customerLoss",customerLoss);
        return "customer_reprieve";
    }

    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> queryCustomerLinkman(CustomerReprieveQuery customerReprieveQuery){
        return customerReprieveService.queryByParamsForDataGrid(customerReprieveQuery);
    }

    @RequestMapping("save")
    @ResponseBody
    public ResultInfo saveCustomerLinkman(CustomerReprieve customerReprieve){
        customerReprieveService.saveCustomerReprieve(customerReprieve);
        return success("数据添加成功！");
    }

    @RequestMapping("update")
    @ResponseBody
    public ResultInfo updateCustomerLinkman(CustomerReprieve customerReprieve){
        customerReprieveService.updateCustomerReprieve(customerReprieve);
        return success("数据更新成功！");
    }

    @RequestMapping("delete")
    @ResponseBody
    public ResultInfo deleteCustomerLinkman(Integer id){
        customerReprieveService.deleteCustomerReprieve(id);
        return success("数据删除成功！");
    }

}
