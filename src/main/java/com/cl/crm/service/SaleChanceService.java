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

    public void updateSaleChance(SaleChance saleChance){
        /**
         * 1.参数校验
         *      id 记录存在校验
         *      customerName:非空
         *      linkMan:非空
         *      linkPhone:非空 11位手机号
         * 2. 设置相关参数值
         *      updateDate:系统当前时间
         *         原始记录 未分配 修改后改为已分配(由分配人决定)
         *            state 0->1
         *            assginTime 系统当前时间
         *            devResult 0-->1
         *         原始记录  已分配  修改后 为未分配
         *            state  1-->0
         *            assignTime  待定  null
         *            devResult 1-->0
         * 3.执行更新 判断结果
         */

        AssertUtil.isTrue(null==saleChance.getId(),"待更新记录不存在！");
        SaleChance temp = selectByPrimaryKey(saleChance.getId());
        AssertUtil.isTrue(null==temp,"待更新记录不存在！");
        checkParams(saleChance.getCustomerName(),saleChance.getLinkMan(),saleChance.getLinkPhone());
        saleChance.setUpdateDate(new Date());
        if(StringUtils.isBlank(temp.getAssignMan()) && StringUtils.isNotBlank(saleChance.getAssignMan())){
            saleChance.setState(StateStatus.STATED.getType());
            saleChance.setAssignTime(new Date());
            saleChance.setDevResult(DevResult.DEVING.getType());
        }else if(StringUtils.isNotBlank(temp.getAssignMan()) && StringUtils.isBlank(saleChance.getAssignMan())){
            saleChance.setAssignMan("");
            saleChance.setState(StateStatus.UNSTATE.getType());
            saleChance.setAssignTime(null);
            saleChance.setDevResult(DevResult.UNDEV.getType());
        }
        AssertUtil.isTrue(updateByPrimaryKeySelective(saleChance)<1,"数据更新失败！");
    }

    public void deleteSaleChance(Integer[] ids){
        AssertUtil.isTrue(ids.length==0 || null==ids,"待删除数据为空！");
        AssertUtil.isTrue(ids.length>deleteBatch(ids),"删除数据失败！");
    }

    public void updateDevResult(Integer devResult,Integer id){
        AssertUtil.isTrue(null==id,"记录不存在！");
        SaleChance temp = selectByPrimaryKey(id);
        AssertUtil.isTrue(null==temp,"记录不存在！");
        temp.setDevResult(devResult);
        AssertUtil.isTrue(updateByPrimaryKeySelective(temp)<1,"操作失败！");
    }
}
