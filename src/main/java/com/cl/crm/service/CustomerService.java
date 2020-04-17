package com.cl.crm.service;

import com.cl.base.BaseService;
import com.cl.crm.dao.CustomerLossMapper;
import com.cl.crm.dao.CustomerMapper;
import com.cl.crm.dao.CustomerOrderMapper;
import com.cl.crm.po.Customer;
import com.cl.crm.po.CustomerLoss;
import com.cl.crm.po.CustomerOrder;
import com.cl.crm.utils.AssertUtil;
import com.cl.crm.utils.PhoneUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CustomerService extends BaseService<Customer,Integer> {

    @Resource
    private CustomerMapper customerMapper;

    @Resource
    private CustomerOrderMapper customerOrderMapper;

    @Resource
    private CustomerLossMapper customerLossMapper;

    @Transactional(propagation = Propagation.REQUIRED)
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

    @Transactional(propagation = Propagation.REQUIRED)
    public void updateCustomer(Customer customer) {
        Customer temp = selectByPrimaryKey(customer.getId());
        AssertUtil.isTrue(null==customer.getId() && null==temp.getId(),"待更新信息不存在！");
        checkParams(customer.getName(),customer.getFr(),customer.getPhone());
        temp = customerMapper.queryCustomerByName(customer.getName());
        AssertUtil.isTrue(null!=temp && !(temp.getId().equals(customer.getId())),"客户名称已存在！");
        customer.setUpdateDate(new Date());
        AssertUtil.isTrue(updateByPrimaryKeySelective(customer)<1,"客户信息更新失败！");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteCustomer(Integer id) {
        Customer temp = selectByPrimaryKey(id);
        AssertUtil.isTrue(null==id && null==temp.getId(),"待删除信息不存在！");
        temp.setIsValid(0);
        AssertUtil.isTrue(updateByPrimaryKeySelective(temp)<1,"客户信息删除失败！");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void queryCustomerLoss(){
        List<Customer> customers = customerMapper.queryCustomerLoss();
        if(null!=customers && customers.size()>0){
            List<CustomerLoss> customerLosses= new ArrayList<CustomerLoss>();
            ArrayList<Integer> lossCusIds = new ArrayList<Integer>();
            customers.forEach(customer -> {
                CustomerLoss customerLoss = new CustomerLoss();
                customerLoss.setCusNo(customer.getKhno());
                customerLoss.setCusName(customer.getName());
                customerLoss.setCreateDate(new Date());
                customerLoss.setCusManager(customer.getCusManager());
                customerLoss.setIsValid(1);
                //  设置客户流失状态为暂缓流失状态
                customerLoss.setState(0);
                customerLoss.setUpdateDate(new Date());
                //设置最近下单时间
                CustomerOrder customerOrder = customerOrderMapper.queryLastCustomerOrderByCusId(customer.getId());
                if(customerOrder!=null){
                    customerLoss.setLastOrderTime(customerOrder.getOrderDate());
                }
                customerLosses.add(customerLoss);
                lossCusIds.add(customer.getId());

            });
            AssertUtil.isTrue(customerLossMapper.insertBatch(customerLosses)<customerLosses.size(),"客户流失数据流转失败！");
            //更新客户表中的流失状态
            AssertUtil.isTrue(customerMapper.updateStateBatchByIds(lossCusIds)<customerLosses.size(),"客户流失数据流转失败！");

        }

    }

}
