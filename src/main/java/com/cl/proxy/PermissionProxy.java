package com.cl.proxy;

import com.cl.crm.annotaions.RequirePermission;
import com.cl.crm.exceptions.AuthFailedException;
import com.cl.crm.po.Permission;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.List;

@Component
@Aspect
public class PermissionProxy {

    /*@Pointcut(value ="@annotation(com.cl.crm.annotaions.RequirePermission)" )
    public void cut(){}*/

    @Resource
    private HttpSession session;

//    @Around(value = "cut()")
    @Around(value = "@annotation(com.cl.crm.annotaions.RequirePermission)")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        List<Permission> permissions = (List<Permission>) session.getAttribute("permissions");
        if(null==permissions || permissions.size()==0){
            throw new AuthFailedException();
        }
        //获取方法签名
        MethodSignature  methodSignature = (MethodSignature) pjp.getSignature();
        //获取方法的RequirePermission注解
        RequirePermission requirePermission = methodSignature.getMethod().getDeclaredAnnotation(RequirePermission.class);
        if(!permissions.contains(requirePermission.code())){
            throw new AuthFailedException();
        }
        Object result=null;
        result=pjp.proceed();
        return result;
    }
}
