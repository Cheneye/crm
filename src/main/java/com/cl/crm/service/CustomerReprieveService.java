package com.cl.crm.service;

import com.cl.base.BaseService;
import com.cl.crm.dao.CustomerReprieveMapper;
import com.cl.crm.po.CustomerReprieve;
import com.cl.crm.utils.AssertUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class CustomerReprieveService extends BaseService<CustomerReprieve,Integer> {

    @Resource
    private CustomerReprieveMapper customerReprieveMapper;

    @Transactional(propagation = Propagation.REQUIRED)
    public void saveCustomerReprieve(CustomerReprieve customerReprieve) {
        AssertUtil.isTrue(null==customerReprieve.getMeasure(),"暂缓措施不能为空！");
        customerReprieve.setIsValid(1);
        customerReprieve.setCreateDate(new Date());
        customerReprieve.setUpdateDate(new Date());
        AssertUtil.isTrue(insertSelective(customerReprieve)<1,"添加失败！");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void updateCustomerReprieve(CustomerReprieve customerReprieve) {
        CustomerReprieve temp = selectByPrimaryKey(customerReprieve.getId());
        AssertUtil.isTrue(null==customerReprieve.getId()||null==temp,"待更新数据不存在！");
        AssertUtil.isTrue(null==customerReprieve.getMeasure(),"暂缓措施不能为空！");
        customerReprieve.setUpdateDate(new Date());
        AssertUtil.isTrue(updateByPrimaryKeySelective(customerReprieve)<1,"更新失败！");

    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteCustomerReprieve(Integer id) {
        CustomerReprieve temp = selectByPrimaryKey(id);
        AssertUtil.isTrue(null==id||null==temp,"待更新数据不存在！");
        temp.setIsValid(0);
        AssertUtil.isTrue(updateByPrimaryKeySelective(temp)<1,"删除失败！");
    }
}
