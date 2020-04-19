package com.cl.crm.controller;

import com.cl.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("report")
public class ReportController extends BaseController {


    @RequestMapping("{type}")
    public String index(@PathVariable Integer type){
        if(null !=type){
            if(type ==1){
                return "customer_contribution";
            }else if(type==2){
                return "customer_make";
            }else{
                return "customer_service";
            }
        }else{
            return "";
        }
    }
}
