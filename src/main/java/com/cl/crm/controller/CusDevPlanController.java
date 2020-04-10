package com.cl.crm.controller;

import com.cl.base.BaseController;
import com.cl.crm.modal.ResultInfo;
import com.cl.crm.po.CusDevPlan;
import com.cl.crm.query.CusDevPlanQuery;
import com.cl.crm.service.CusDevPlanService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping("cus_dev_plan")
public class CusDevPlanController extends BaseController {

    @Resource
    private CusDevPlanService cusDevPlanService;

    @RequestMapping("index")
    public String index(){
        return "cus_dev_plan";
    }

    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> queryCusDevPlansByParams(CusDevPlanQuery cusDevPlanQuery){
        return cusDevPlanService.queryByParamsForDataGrid(cusDevPlanQuery);
    }

    @RequestMapping("save")
    @ResponseBody
    public ResultInfo saveCusDevPlan(CusDevPlan cusDevPlan){
        cusDevPlanService.saveCusDevPlan(cusDevPlan);
        return success("数据添加成功！");
    }

    @RequestMapping("update")
    @ResponseBody
    public ResultInfo updateCusDevPlan(CusDevPlan cusDevPlan){
        cusDevPlanService.updateCusDevPlan(cusDevPlan);
        return success("数据更新成功！");
    }

    @RequestMapping("delete")
    @ResponseBody
    public ResultInfo deleteCusDevPlan(Integer id){
        cusDevPlanService.deleteCusDevPlan(id);
        return success("数据删除成功！");
    }

}
