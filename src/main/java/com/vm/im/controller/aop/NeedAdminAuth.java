package com.vm.im.controller.aop;

import com.alibaba.fastjson.JSON;
import com.vm.im.common.annot.AdminAuth;
import com.vm.im.common.enums.AdminRoleEnum;
import com.vm.im.common.enums.BusinessExceptionEnum;
import com.vm.im.common.exception.BusinessException;
import com.vm.im.common.util.HMACSHA256Util;
import com.vm.im.common.util.StringUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;

import static com.vm.im.common.constant.CommonConstant.AUTHORIZATION;

/**
 * @ClassName: NeedAdminAuth
 * @Description:
 * @Author
 * @Date
 * @Version 1.0
 */
@Aspect
@Component
public class NeedAdminAuth {
    private static final Logger LOG = LoggerFactory.getLogger(NeedAdminAuth.class);

    @Value("${admin.secret}")
    private String secret;

    @Around("@annotation(com.vm.im.common.annot.AdminAuth)")
    public String checkAuth(ProceedingJoinPoint joinPoint) throws Throwable {
        String authorization = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader(AUTHORIZATION);

        if (StringUtil.isBlank(authorization)) {
            LOG.info("该操作需要认证用户身份, AUTHORIZATION 不能为空");
            throw new BusinessException(BusinessExceptionEnum.ADMIN_AUTH_EXCEPTION.getFailCode(), BusinessExceptionEnum.ADMIN_AUTH_EXCEPTION.getFailReason());
        }

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        AdminAuth annotation = methodSignature.getMethod().getAnnotation(AdminAuth.class);

        if (annotation.auth()) {
            AdminRoleEnum[] roles = annotation.roles();
            //查询redis 校验用户
            boolean contains = Arrays.asList(roles).contains(AdminRoleEnum.ADMIN);
            if (contains) {
                String message = JSON.toJSONString(joinPoint.getArgs()[0]);
                String sha256hmac = HMACSHA256Util.sha256_HMAC(message, secret);
                if (!authorization.equals(sha256hmac)) {
                    LOG.info("请求认证不通过, 非法的请求, args:{}, authorization:{},sha256hmac:{}", message, authorization, sha256hmac);

                    throw new BusinessException(BusinessExceptionEnum.ADMIN_AUTH_EXCEPTION.getFailCode(), BusinessExceptionEnum.ADMIN_AUTH_EXCEPTION.getFailReason());
                }
            }
        }
        return (String) joinPoint.proceed();
    }
}
