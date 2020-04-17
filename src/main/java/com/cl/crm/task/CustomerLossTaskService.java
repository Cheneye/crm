package com.cl.crm.task;


import com.cl.crm.service.CustomerService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class CustomerLossTaskService {
    @Resource
    private CustomerService customerService;

    //@Scheduled(cron = "0/5 * * * * ?")
    public void jop(){
        System.out.println("定时任务开始...."+new Date());
        customerService.queryCustomerLoss();
    }
}
