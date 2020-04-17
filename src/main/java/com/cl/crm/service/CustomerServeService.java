package com.cl.crm.service;

import com.cl.base.BaseService;
import com.cl.crm.dao.CustomerMapper;
import com.cl.crm.dao.UserMapper;
import com.cl.crm.enums.CustomerServeStatus;
import com.cl.crm.po.Customer;
import com.cl.crm.po.CustomerServe;
import com.cl.crm.utils.AssertUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class CustomerServeService extends BaseService<CustomerServe,Integer> {

    @Resource
    private CustomerMapper customerMapper;

    @Resource
    private UserMapper userMapper;

    public void saveOrUpdateCustomerServe(CustomerServe customerServe) {
        if(null==customerServe.getId()){
            AssertUtil.isTrue(StringUtils.isBlank(customerServe.getCustomer()),"客户不能为空！");
            AssertUtil.isTrue(null==customerMapper.queryCustomerByName(customerServe.getCustomer()),"当前客户不存在！");
            AssertUtil.isTrue(StringUtils.isBlank(customerServe.getServeType()),"服务类型不能为空！");
            customerServe.setState(CustomerServeStatus.ASSIGNED.getStatus());
            customerServe.setIsValid(1);
            customerServe.setCreateDate(new Date());
            customerServe.setUpdateDate(new Date());
            AssertUtil.isTrue(insertSelective(customerServe)<1,"服务创建失败！");
        }else{
            CustomerServe temp = selectByPrimaryKey(customerServe.getId());
            AssertUtil.isTrue(null==temp,"待分配的数据不存在！|");
            if((customerServe.getState()).equals(CustomerServeStatus.ASSIGNED.getStatus())){
                /**
                 * 服务分配
                 */
                AssertUtil.isTrue(StringUtils.isBlank(customerServe.getAssigner()),"待分配用户不能为空！");
                AssertUtil.isTrue(null==userMapper.selectByPrimaryKey(Integer.parseInt(customerServe.getAssigner())),"待分配用户不存在！");
                customerServe.setState(CustomerServeStatus.PROCED.getStatus());
                customerServe.setUpdateDate(new Date());
                customerServe.setAssignTime(new Date());
                AssertUtil.isTrue(updateByPrimaryKeySelective(customerServe)<1,"服务分配失败！");
            }else if((customerServe.getState()).equals(CustomerServeStatus.PROCED.getStatus())){
                /**
                 * 服务处理
                 */
                AssertUtil.isTrue(StringUtils.isBlank(customerServe.getServiceProce()),"请指定处理内容！");
                customerServe.setServiceProceTime(new Date());
                customerServe.setState(CustomerServeStatus.FEED_BACK.getStatus());
                customerServe.setUpdateDate(new Date());
                customerServe.setServiceProceTime(new Date());
                AssertUtil.isTrue(updateByPrimaryKeySelective(customerServe)<1,"服务处理失败！");
            }else if((customerServe.getState()).equals(CustomerServeStatus.FEED_BACK.getStatus())){
                /**
                 * 服务反馈
                 */
                AssertUtil.isTrue(StringUtils.isBlank(customerServe.getServiceProceResult()),"待处理结果不能为空！");
                AssertUtil.isTrue(StringUtils.isBlank(customerServe.getMyd()),"反馈满意度不能为空！");
                customerServe.setState(CustomerServeStatus.ARCHIVED.getStatus());
                customerServe.setUpdateDate(new Date());
                AssertUtil.isTrue(updateByPrimaryKeySelective(customerServe)<1,"服务反馈失败！");
            }


        }

    }
}
