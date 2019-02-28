package com.vm.im.common.annot;

import com.vm.im.common.enums.GroupRoleEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 是否需要权限认证 有些注解修饰 表示一定需要TOken
 * 如果没有此注解修饰 则有传Token就校验 没传就不校验
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface GroupAuth {

    boolean auth() default true;

    GroupRoleEnum[] roles() default {};
}
