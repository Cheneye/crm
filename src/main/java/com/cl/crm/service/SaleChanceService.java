package com.cl.crm.service;

import com.cl.base.BaseService;
import com.cl.crm.enums.DevResult;
import com.cl.crm.enums.StateStatus;
import com.cl.crm.po.SaleChance;
import com.cl.crm.query.SaleChanceQuery;
import com.cl.crm.utils.AssertUtil;
import com.cl.crm.utils.PhoneUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@Service
public class SaleChanceService extends BaseService<SaleChance,Integer> {

    public Map<String,Object> querySaleChancesByParams(SaleChanceQuery saleChanceQuery){
        HashMap<String, Object> map = new HashMap<>();
        PageHelper.startPage(saleChanceQuery.getPage(),saleChanceQuery.getRows());
        PageInfo<SaleChance> pageInfo = new PageInfo<>(selectByParams(saleChanceQuery));
        map.put("total",pageInfo.getTotal());
        map.put("rows",pageInfo.getList());
        return map;
    }

    public void saveSaleChance(SaleChance saleChance){
        /**
         * 1.参数校验
         *   customerName:非空
         *   linkMan:非空
         *   linkPhone:非空 11位手机号
         * 2.设置相关参数默认值
         *      state:默认未分配  如果选择分配人  state 为已分配
         *      assignTime:如果  如果选择分配人   时间为当前系统时间
         *      devResult:默认未开发   如果选择分配人  devResult为开发中
         *      isValid :默认有效
         *      createDate updateDate:默认当前系统时间
         *  3.执行添加 判断结果
         */
        checkParams(saleChance.getCustomerName(),saleChance.getLinkMan(),saleChance.getLinkPhone());
        saleChance.setState(StateStatus.UNSTATE.getType());
        saleChance.setDevResult(DevResult.UNDEV.getType());
        if(StringUtils.isNotBlank(saleChance.getAssignMan())){
            saleChance.setState(StateStatus.STATED.getType());
            saleChance.setDevResult(DevResult.DEVING.getType());
            saleChance.setAssignTime(new Date());
        }
        saleChance.setIsValid(1);
        saleChance.setCreateDate(new Date());
        saleChance.setUpdateDate(new Date());
        Integer row = insertSelective(saleChance);
        AssertUtil.isTrue(row<1,"数据添加失败！");
    }

    private void checkParams(String customerName, String linkMan, String linkPhone) {
        AssertUtil.isTrue(StringUtils.isBlank(customerName),"客户名不能为空！");
        AssertUtil.isTrue(StringUtils.isBlank(linkMan),"联系人不能为空！");
        AssertUtil.isTrue(StringUtils.isBlank(linkPhone),"联系电话不能为空！");
        AssertUtil.isTrue(!(PhoneUtil.isMobile(linkPhone)),"联系电话输入不合法！");
    }

}
