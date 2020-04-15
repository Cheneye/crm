package com.cl.crm.service;

import com.cl.base.BaseService;
import com.cl.crm.dao.CustomerLinkmanMapper;
import com.cl.crm.po.CustomerLinkman;
import com.cl.crm.utils.AssertUtil;
import com.cl.crm.utils.PhoneUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class CustomerLinkmanService extends BaseService<CustomerLinkman,Integer> {

    @Resource
    private CustomerLinkmanMapper customerLinkmanMapper;

    public void saveCustomerLinkman(CustomerLinkman customerLinkman) {
        checkParams(customerLinkman.getLinkName(),customerLinkman.getZhiwei(),customerLinkman.getPhone());
        CustomerLinkman temp = customerLinkmanMapper.queryByPhone(customerLinkman.getPhone(),customerLinkman.getCusId());
        AssertUtil.isTrue(null!=temp,"电话号码已存在！");
        customerLinkman.setIsValid(1);
        customerLinkman.setCeateDate(new Date());
        customerLinkman.setUpdateDate(new Date());
        AssertUtil.isTrue(insertSelective(customerLinkman)<1,"添加失败！");
    }

    private void checkParams(String linkName, String zhiwei, String phone) {
        AssertUtil.isTrue(StringUtils.isBlank(linkName),"联系人不能为空！");
        AssertUtil.isTrue(StringUtils.isBlank(zhiwei),"职位不能为空！");
        AssertUtil.isTrue(!PhoneUtil.isMobile(phone),"电话号码格式不合法！");
    }

    public void updateCustomerLinkman(CustomerLinkman customerLinkman) {
        AssertUtil.isTrue(null==customerLinkman.getId() || null==selectByPrimaryKey(customerLinkman.getId()),"待更新数据不存在！");
        checkParams(customerLinkman.getLinkName(),customerLinkman.getZhiwei(),customerLinkman.getPhone());
        CustomerLinkman linkman = customerLinkmanMapper.queryByPhone(customerLinkman.getPhone(),customerLinkman.getCusId());
        AssertUtil.isTrue(null!=linkman && !(linkman.getId().equals(customerLinkman.getId())),"电话号码已存在！");
        customerLinkman.setUpdateDate(new Date());
        AssertUtil.isTrue(updateByPrimaryKeySelective(customerLinkman)<1,"更新失败！");
    }

    public void deleteCustomerLinkman(Integer id) {
        CustomerLinkman temp = selectByPrimaryKey(id);
        AssertUtil.isTrue(null==id ||null==temp,"待删除数据不存在！");
        temp.setIsValid(0);
        AssertUtil.isTrue(updateByPrimaryKeySelective(temp)<1,"删除失败！");
    }
}
