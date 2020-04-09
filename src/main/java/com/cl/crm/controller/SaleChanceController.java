package com.cl.crm.controller;

import com.cl.base.BaseController;
import com.cl.crm.modal.ResultInfo;
import com.cl.crm.po.SaleChance;
import com.cl.crm.query.SaleChanceQuery;
import com.cl.crm.service.SaleChanceService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping("sale_chance")
public class SaleChanceController extends BaseController {

    @Resource
    private SaleChanceService saleChanceService;

    @RequestMapping("index")
    public String index(){
        return "sale_chance";
    }

    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> querySaleChancesByParams(SaleChanceQuery saleChanceQuery){
        return saleChanceService.querySaleChancesByParams(saleChanceQuery);
    }

    @RequestMapping("save")
    @ResponseBody
    public ResultInfo saveSaleChance(SaleChance saleChance){
        saleChanceService.saveSaleChance(saleChance);
        return success("数据添加成功！");
    }
}
