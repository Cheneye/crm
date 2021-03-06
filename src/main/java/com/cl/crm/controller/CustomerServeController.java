package com.cl.crm.controller;

import com.cl.base.BaseController;
import com.cl.crm.modal.ResultInfo;
import com.cl.crm.po.CustomerServe;
import com.cl.crm.query.CustomerServeQuery;
import com.cl.crm.service.CustomerServeService;
import com.cl.crm.utils.LoginUserUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("customer_serve")
public class CustomerServeController extends BaseController {

    @Resource
    private CustomerServeService customerServeService;

    @RequestMapping("index/{type}")
    public String index(@PathVariable Integer type){
        if (type==1){
            return "customer_serve_create";
        }else if(type==2){
            return "customer_serve_assign";
        }else if(type==3){
            return "customer_serve_proce";
        }else if(type==4){
            return "customer_serve_feed_back";
        }else if(type==5){
            return "customer_serve_archive";
        }else{
            return "";
        }

    }

    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> queryModulesByParams(Integer flag, HttpServletRequest request, CustomerServeQuery customerServeQuery){
        if(null!=flag && flag==1){
            customerServeQuery.setAssigner(LoginUserUtil.releaseUserIdFromCookie(request));
        }
        return customerServeService.queryByParamsForDataGrid(customerServeQuery);
    }

    @RequestMapping("saveOrUpdateCustomerServe")
    @ResponseBody
    public ResultInfo saveOrUpdateCustomerServe(CustomerServe customerServe){
        customerServeService.saveOrUpdateCustomerServe(customerServe);
        return success("操作成功！");
    }




}
