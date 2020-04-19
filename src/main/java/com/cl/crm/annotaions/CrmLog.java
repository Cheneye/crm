package com.cl.crm.annotaions;


import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
/**
 * 设置自定义注解
 */
public @interface CrmLog {
    String Module() default "";
    String Operation() default "";
}
