package com.doumies.common.mybatis.annotation;

import java.lang.annotation.*;

/**
 * 数据权限注解
 *
 * @author Taylor
 * @Date 2023-2-25 15:48
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface DataPermission {

    /**
     * 数据权限 {@link com.baomidou.mybatisplus.extension.plugins.inner.DataPermissionInterceptor}
     */
    String deptAlias() default "";
}

