package com.cl.crm.service;

import com.cl.base.BaseService;
import com.cl.crm.po.CusDevPlan;
import com.cl.crm.po.SaleChance;
import com.cl.crm.utils.AssertUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;


@Service
public class CusDevPlanService extends BaseService<CusDevPlan,Integer> {

    @Resource
    private SaleChanceService saleChanceService;

    public void saveCusDevPlan(CusDevPlan cusDevPlan){
        /**
         * 1.参数校验
         *     营销机会id 非空 记录必须存在
         *     计划项内容  非空
         *     计划项时间 非空
         * 2.参数默认值设置
         *    is_valid createDate  updateDate
         *
         *  3.执行添加  判断结果
         */
        checkParams(cusDevPlan.getSaleChanceId(),cusDevPlan.getPlanItem(),cusDevPlan.getPlanDate());
        cusDevPlan.setIsValid(1);
        cusDevPlan.setCreateDate(new Date());
        cusDevPlan.setUpdateDate(new Date());
        AssertUtil.isTrue(insertSelective(cusDevPlan)<1,"数据添加失败！");
    }

    private void checkParams(Integer saleChanceId, String planItem, Date planDate) {
        SaleChance temp = saleChanceService.selectByPrimaryKey(saleChanceId);
        AssertUtil.isTrue(null==saleChanceId||null==temp,"该营销机会不存在！");
        AssertUtil.isTrue(null==planDate,"未指定计划项日期！");
        AssertUtil.isTrue(StringUtils.isBlank(planItem),"未输入计划项内容！");
    }

    public void updateCusDevPlan(CusDevPlan cusDevPlan){
        /**
         * 1.参数校验
         *     id  非空 记录存在
         *     营销机会id 非空 记录必须存在
         *     计划项内容  非空
         *     计划项时间 非空
         * 2.参数默认值设置
         updateDate
         *  3.执行更新  判断结果
         */
        AssertUtil.isTrue(null==cusDevPlan.getId()||null==selectByPrimaryKey(cusDevPlan.getId()),"待更新记录不存在！");
        checkParams(cusDevPlan.getSaleChanceId(),cusDevPlan.getPlanItem(),cusDevPlan.getPlanDate());
        cusDevPlan.setUpdateDate(new Date());
        AssertUtil.isTrue(updateByPrimaryKeySelective(cusDevPlan)<1,"计划项记录更新失败！");
    }

    public void deleteCusDevPlan(Integer id){
        CusDevPlan cusDevPlan = selectByPrimaryKey(id);
        AssertUtil.isTrue(null==id||null==cusDevPlan,"待删除记录不存在！");
        cusDevPlan.setIsValid(0);
        AssertUtil.isTrue(updateByPrimaryKeySelective(cusDevPlan)<1,"删除失败！");
    }
}
