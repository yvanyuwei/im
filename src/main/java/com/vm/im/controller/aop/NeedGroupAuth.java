package com.vm.im.controller.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

/**
 * @ClassName: NeedGroupAuth
 * @Description:
 * @Author zhangqi
 * @Date 2019年02月21日17时27分
 * @Version 1.0
 */
@Aspect
@Component
public class NeedGroupAuth {

    @Around("@annotation(com.vm.im.common.annot.GroupAuth)")
    public String checkAuth(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
//      return "{\"errno\":\"-2\",\"errmsg\":\"管理员权限不足\"}";
        return (String) joinPoint.proceed();
//            }
    }
}
