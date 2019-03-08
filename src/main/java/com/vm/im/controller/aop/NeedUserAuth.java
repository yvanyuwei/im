package com.vm.im.controller.aop;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vm.im.common.annot.UserAuth;
import com.vm.im.common.constant.CommonConstant;
import com.vm.im.common.enums.BusinessExceptionEnum;
import com.vm.im.common.enums.GroupRoleEnum;
import com.vm.im.common.exception.BusinessException;
import com.vm.im.common.util.RedisUtil;
import com.vm.im.common.util.StringUtil;
import com.vm.im.entity.user.UserChatGroup;
import com.vm.im.service.user.UserChatGroupService;
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


import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;

import static com.vm.im.common.constant.CommonConstant.*;

/**
 * @ClassName: NeedGroupAuth
 * @Description:
 * @Author zhangqi
 * @Date 2019年02月21日17时27分
 * @Version 1.0
 */
@Aspect
@Component
public class NeedUserAuth {
    private static final Logger LOG = LoggerFactory.getLogger(NeedUserAuth.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private UserChatGroupService userChatGroupService;


    @Around("@annotation(com.vm.im.common.annot.UserAuth)")
    public String checkAuth(ProceedingJoinPoint joinPoint) throws Throwable {
        checkToken();

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        UserAuth annotation = methodSignature.getMethod().getAnnotation(UserAuth.class);

        if (annotation.auth()) {
            GroupRoleEnum[] roles = annotation.roles();
            if (roles.length >= CommonConstant.YES) {
                String args = JSON.toJSONString(joinPoint.getArgs()[0]);
                checkGroupRole(roles, args);
            }
        }

        return (String) joinPoint.proceed();
    }

    /**
     * 校验用户群组权限
     *
     * @param roles 需要的权限
     * @param args
     */
    private void checkGroupRole(GroupRoleEnum[] roles, String args) throws IOException {
        LOG.info("开始用户群组权限认证");

        JsonNode ret = objectMapper.readTree(args);
        String chatGroupId = ret.get(CHAT_GROUP_ID).asText();
        String uid = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader(USERID);
        UserChatGroup userChatGroup = userChatGroupService.selectUserByGroupIdAndUid(chatGroupId, uid);
        if (userChatGroup == null || userChatGroup.getDelFlag() == CommonConstant.YES) {
            LOG.info("用户群组不存在, chatGroupId:{}, uid:{}", chatGroupId, uid);
            throw new BusinessException(BusinessExceptionEnum.GROUP_NOT_FOUND_EXCEPTION.getFailCode(), BusinessExceptionEnum.GROUP_NOT_FOUND_EXCEPTION.getFailReason());
        }

        boolean contains = Arrays.asList(roles).contains(GroupRoleEnum.valueOf(userChatGroup.getType()));
        if (!contains) {
            LOG.info("用户无访问权限, 请求失败, chatGroupId:{}, uid:{}", chatGroupId, uid);
            throw new BusinessException(BusinessExceptionEnum.USER_AUTH_EXCEPTION.getFailCode(), BusinessExceptionEnum.USER_AUTH_EXCEPTION.getFailReason());
        }

    }

    /**
     * 校验用户token 是否有效
     *
     * @return 用户信息
     */
    public String checkToken() {
        LOG.info("开始用户身份认证");
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String authorization = request.getHeader(AUTHORIZATION);
        String uid = request.getHeader(USERID);

        if (StringUtil.isBlank(authorization) || StringUtil.isBlank(uid)) {
            LOG.info("该操作需要认证用户身份, AUTHORIZATION, USERID 不能为空");
            throw new BusinessException(BusinessExceptionEnum.USER_AUTH_EXCEPTION.getFailCode(), BusinessExceptionEnum.USER_AUTH_EXCEPTION.getFailReason());
        }
        LOG.info("uid:{}, authorization:{}", uid, authorization);

        String userInfo = (String) redisUtil.get(REDIS_TOKEN_PREFIX + uid);
        if (StringUtil.isEmpty(userInfo)) {
            LOG.info("用户token 已过期, uid:{}", uid);
            throw new BusinessException(BusinessExceptionEnum.USER_AUTH_EXCEPTION.getFailCode(), BusinessExceptionEnum.USER_AUTH_EXCEPTION.getFailReason());
        }

        LOG.info("userInfo:{}", userInfo);
        JsonNode ret = null;
        String token = null;
        try {
            ret = objectMapper.readTree(userInfo);
            token = ret.get(REDIS_TOKEN).asText();
        } catch (IOException e) {
            LOG.info("用户信息转换异常");
        }
        if (!authorization.equals(token)) {
            LOG.info("用户token 认证失败, uid:{}", uid);
            throw new BusinessException(BusinessExceptionEnum.USER_AUTH_EXCEPTION.getFailCode(), BusinessExceptionEnum.USER_AUTH_EXCEPTION.getFailReason());
        }
        return userInfo;
    }

}
