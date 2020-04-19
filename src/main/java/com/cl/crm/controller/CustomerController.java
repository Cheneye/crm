package com.cl.crm.controller;

import com.cl.base.BaseController;
import com.cl.crm.modal.ResultInfo;
import com.cl.crm.po.Customer;
import com.cl.crm.query.CustomerQuery;
import com.cl.crm.service.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping("customer")
public class CustomerController extends BaseController {

    @Resource
    private CustomerService customerService;


    @RequestMapping("/index")
    public String index(){
        return "customer";
    }

    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> queryCustomerByParams(CustomerQuery customerQuery){
        return customerService.queryByParamsForDataGrid(customerQuery);
    }

    @RequestMapping("save")
    @ResponseBody
    public ResultInfo saveCustomer(Customer customer){
        customerService.saveCustomer(customer);
        return success("客户信息添加成功！");
    }

    @RequestMapping("update")
    @ResponseBody
    public ResultInfo updateCustomer(Customer customer){
        customerService.updateCustomer(customer);
        return success("客户信息更新成功！");
    }

    @RequestMapping("delete")
    @ResponseBody
    public ResultInfo deleteCustomer(Integer id){
        customerService.deleteCustomer(id);
        return success("客户信息删除成功！");
    }

    @RequestMapping("queryCustomerInfo")
    public String queryCustomerInfo(Integer cusId, Model model){
        Customer customer = customerService.selectByPrimaryKey(cusId);
        model.addAttribute("customer",customer);
        return "customer_order";
    }

    @RequestMapping("queryCustomerContributionByParams")
    @ResponseBody
    public Map<String,Object> queryCustomerContributionByParams(CustomerQuery customerQuery){
        return customerService.queryCustomerContributionByParams(customerQuery);
    }

    @RequestMapping("queryCustomerMake")
    @ResponseBody
    public Map<String,Object> queryCustomerMake(){
        return customerService.queryCustomerMake();
    }

    @RequestMapping("queryCustomerService")
    @ResponseBody
    public Map<String,Object> queryCustomerService(){
        return customerService.queryCustomerService();
    }

}
