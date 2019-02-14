package com.htkfood.interceptor.annotation;
import java.lang.annotation.*;

/**
 * 授权类型
 *
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Privileges {
    /**
     * 授权类型
     * @return
     */
    String[] value();
}