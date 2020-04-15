package com.cl.crm.service;

import com.cl.base.BaseService;
import com.cl.crm.dao.CustomerMapper;
import com.cl.crm.po.Customer;
import com.cl.crm.utils.AssertUtil;
import com.cl.crm.utils.PhoneUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.SimpleFormatter;

@Service
public class CustomerService extends BaseService<Customer,Integer> {

    @Resource
    private CustomerMapper customerMapper;

    public void saveCustomer(Customer customer) {
        /**
         * 1.参数校验
         *    客户名称 name 非空  不可重复
         *    phone 联系电话  非空  格式符合规范
         *    法人  非空
         * 2.默认值设置
         *     isValid  state  cteaetDate  updadteDate
         *      khno 系统生成 唯一  (uuid| 时间戳 | 年月日时分秒  雪花算法)
         *3.执行添加  判断结果
         */
        checkParams(customer.getName(),customer.getFr(),customer.getPhone());
        Customer temp=customerMapper.queryCustomerByName(customer.getName());
        AssertUtil.isTrue(null!=temp,"客户名称已存在！");
        customer.setIsValid(1);
        customer.setState(0);
        customer.setCreateDate(new Date());
        customer.setUpdateDate(new Date());
        String khno="KH_"+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        customer.setKhno(khno);
        AssertUtil.isTrue(insertSelective(customer)<1,"客户信息添加失败！");

    }

    private void checkParams(String name, String fr, String phone) {
        AssertUtil.isTrue(StringUtils.isBlank(name),"客户名称不能为空！");
        AssertUtil.isTrue(StringUtils.isBlank(fr),"法人不能为空！");
        AssertUtil.isTrue(!PhoneUtil.isMobile(phone),"电话号码格式不合法！");

    }

    public void updateCustomer(Customer customer) {
        Customer temp = selectByPrimaryKey(customer.getId());
        AssertUtil.isTrue(null==customer.getId() && null==temp.getId(),"待更新信息不存在！");
        checkParams(customer.getName(),customer.getFr(),customer.getPhone());
        temp = customerMapper.queryCustomerByName(customer.getName());
        AssertUtil.isTrue(null!=temp && !(temp.getId().equals(customer.getId())),"客户名称已存在！");
        customer.setUpdateDate(new Date());
        AssertUtil.isTrue(updateByPrimaryKeySelective(customer)<1,"客户信息更新失败！");
    }

    public void deleteCustomer(Integer id) {
        Customer temp = selectByPrimaryKey(id);
        AssertUtil.isTrue(null==id && null==temp.getId(),"待删除信息不存在！");
        temp.setIsValid(0);
        AssertUtil.isTrue(updateByPrimaryKeySelective(temp)<1,"客户信息删除失败！");
    }
}
