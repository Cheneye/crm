package com.cl.crm.annotaions;


import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
/**
 * 设置自定义注解
 */
public @interface RequirePermission {
    String code() default "";
}
