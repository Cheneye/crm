package com.cl.crm.controller;

import com.cl.base.BaseController;
import com.cl.crm.modal.ResultInfo;
import com.cl.crm.po.CustomerLinkman;
import com.cl.crm.query.CustomerLinkmanQuery;
import com.cl.crm.service.CustomerLinkmanService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping("customer_linkman")
public class CustomerLinkmanController extends BaseController {

    @Resource
    private CustomerLinkmanService customerLinkmanService;

    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> queryCustomerLinkman(CustomerLinkmanQuery customerLinkmanQuery){
        return customerLinkmanService.queryByParamsForDataGrid(customerLinkmanQuery);
    }

    @RequestMapping("save")
    @ResponseBody
    public ResultInfo saveCustomerLinkman(CustomerLinkman CustomerLinkman){
        customerLinkmanService.saveCustomerLinkman(CustomerLinkman);
        return success("数据添加成功！");
    }

    @RequestMapping("update")
    @ResponseBody
    public ResultInfo updateCustomerLinkman(CustomerLinkman customerLinkman){
        customerLinkmanService.updateCustomerLinkman(customerLinkman);
        return success("数据更新成功！");
    }

    @RequestMapping("delete")
    @ResponseBody
    public ResultInfo deleteCustomerLinkman(Integer id){
        customerLinkmanService.deleteCustomerLinkman(id);
        return success("数据删除成功！");
    }

}
