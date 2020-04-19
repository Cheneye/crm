package com.cl.proxy;

import com.alibaba.fastjson.JSON;
import com.cl.crm.annotaions.CrmLog;
import com.cl.crm.dao.LogMapper;
import com.cl.crm.po.LogWithBLOBs;
import com.cl.crm.service.UserService;
import com.cl.crm.utils.LoginUserUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;


@Component
@Aspect
public class CrmLogProxy {

    @Resource
    private HttpServletRequest request;

    @Resource
    private UserService userService;

    @Resource
    private LogMapper logMapper;

    //环绕通知
    @Around(value = "@annotation(com.cl.crm.annotaions.CrmLog)")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        Object result = null;
        LogWithBLOBs log = new LogWithBLOBs();
        //获取方法签名
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        //获取方法的RequirePermission注解
        CrmLog crmLog = methodSignature.getMethod().getDeclaredAnnotation(CrmLog.class);
        log.setDescription(crmLog.Module()+"_"+crmLog.Operation());
        log.setMethod(methodSignature.toString());
        log.setCreateDate(new Date());
        log.setCreateMan(userService.selectByPrimaryKey(LoginUserUtil.releaseUserIdFromCookie(request)).getTrueName());
        log.setExceptionCode("200");
        if(null!=pjp.getArgs()){
            log.setParams(JSON.toJSONString(pjp.getArgs()));
        }
        log.setRequestIp(request.getLocalAddr());
        log.setType("1");
        long start = System.currentTimeMillis();
        result = pjp.proceed();
        long end = System.currentTimeMillis();
        log.setExecuteTime((int) (end-start));
        log.setResult(JSON.toJSONString(result));
        log.setExceptionDetail("操作成功");
        logMapper.insertSelective(log);
        return result;
    }
}
