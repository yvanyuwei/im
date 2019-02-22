package com.vm.im.controller.aop;

import com.vm.im.common.annot.GroupAuth;
import com.vm.im.common.enums.BusinessExceptionEnum;
import com.vm.im.common.enums.GroupRoleEnum;
import com.vm.im.common.exception.BusinessException;
import com.vm.im.common.util.RedisUtil;
import com.vm.im.common.util.StringUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


import static com.vm.im.common.constant.CommonConstant.AUTHORIZATION;

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
    private static final Logger LOG = LoggerFactory.getLogger(NeedGroupAuth.class);

    @Autowired
    private RedisUtil redisUtil;

    @Around("@annotation(com.vm.im.common.annot.GroupAuth)")
    public String checkAuth(ProceedingJoinPoint joinPoint) throws Throwable {
        String authorization = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader(AUTHORIZATION);

        if (StringUtil.isBlank(authorization)){
            LOG.info("该操作需要认证用户身份, AUTHORIZATION 不能为空");
            throw new BusinessException(BusinessExceptionEnum.USER_AUTH_EXCEPTION.getFailCode(), BusinessExceptionEnum.USER_AUTH_EXCEPTION.getFailReason());
        }

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        GroupAuth annotation = methodSignature.getMethod().getAnnotation(GroupAuth.class);

        if (annotation.auth()){
            GroupRoleEnum[] roles = annotation.roles();
            //查询redis 校验用户
//            boolean contains = Arrays.asList(roles).contains(groupRoleEnum);

        }
        return (String) joinPoint.proceed();
//            }
    }
}
