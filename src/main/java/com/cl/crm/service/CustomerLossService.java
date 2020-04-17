package com.cl.crm.service;

import com.cl.base.BaseService;
import com.cl.crm.po.CustomerLoss;
import com.cl.crm.utils.AssertUtil;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CustomerLossService extends BaseService<CustomerLoss,Integer> {
    public void updateState(String lossReason,Integer lossId) {
        CustomerLoss temp = selectByPrimaryKey(lossId);
        AssertUtil.isTrue(null==lossId||null==temp,"待流失数据不存在！");
        AssertUtil.isTrue(null==lossReason,"流失原因不能为空！");
        temp.setLossReason(lossReason);
        temp.setUpdateDate(new Date());
        temp.setConfirmLossTime(new Date());
        temp.setState(1);
        AssertUtil.isTrue(updateByPrimaryKeySelective(temp)<1,"数据流失失败！");
    }
}
