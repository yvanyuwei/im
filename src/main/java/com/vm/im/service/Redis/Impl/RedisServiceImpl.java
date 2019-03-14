package com.vm.im.service.Redis.Impl;

import com.alibaba.fastjson.JSON;
import com.vm.im.common.constant.CommonConstant;
import com.vm.im.common.enums.BusinessExceptionEnum;
import com.vm.im.common.exception.BusinessException;
import com.vm.im.common.util.RedisUtil;
import com.vm.im.entity.group.ChatGroup;
import com.vm.im.entity.user.User;
import com.vm.im.service.Redis.RedisService;
import com.vm.im.service.group.ChatGroupService;
import com.vm.im.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class RedisServiceImpl implements RedisService {
    private static final Logger LOG = LoggerFactory.getLogger(RedisServiceImpl.class);

    @Autowired
    private RedisUtil redisUtil;

    @Lazy
    @Autowired
    private UserService userService;

    @Autowired
    private ChatGroupService chatGroupService;

    @Override
    public User getRedisUserById(String userId) {
        Object userInfo = redisUtil.hget(CommonConstant.REDIS_USER_INFO, userId);
        User user = null;

        if (null == userInfo) {
            user = userService.getById(userId);
            if (user == null || CommonConstant.YES == user.getDelFlag()) {
                LOG.info("用户不存在, uid:{}", userId);
                throw new BusinessException(BusinessExceptionEnum.USER_NOT_EXIST_EXCEPTION.getFailCode(), BusinessExceptionEnum.USER_AUTH_EXCEPTION.getFailReason());
            } else {
                redisUtil.hset(CommonConstant.REDIS_USER_INFO, userId, JSON.toJSONString(user));
            }

        } else {
            try {
                user = JSON.parseObject((String) userInfo, User.class);
            } catch (Exception e) {
                LOG.info("redis 用户数据解析异常, userInfo:{}", userInfo);
                throw new BusinessException(BusinessExceptionEnum.USER_INFO_PARSING_EXCEPTION.getFailCode(), BusinessExceptionEnum.USER_INFO_PARSING_EXCEPTION.getFailReason());
            }
        }

        LOG.info("redis userInfo: {}", JSON.toJSONString(user));
        return user;
    }

    @Override
    public ChatGroup getRedisGroupMsgByGId(String groupId) {
        Object groupInfo = redisUtil.hget(CommonConstant.REDIS_GROUP_INFO, groupId);
        ChatGroup chatGroup = null;
        if (null == groupInfo){
            chatGroup = chatGroupService.getById(groupId);
            if (null == chatGroup || CommonConstant.YES == chatGroup.getDelFlag()){
                LOG.info("工会不存在，Gid:{}", groupId);
                throw new BusinessException(BusinessExceptionEnum.GROUP_NOT_FOUND_EXCEPTION.getFailCode(),
                        BusinessExceptionEnum.GROUP_NOT_FOUND_EXCEPTION.getFailReason());
            }else{
                redisUtil.hset(CommonConstant.REDIS_GROUP_INFO,groupId,JSON.toJSONString(chatGroup));
            }
        }else {
            try {
                chatGroup = JSON.parseObject(String.valueOf(groupInfo), ChatGroup.class);
            }catch (Exception e){
                LOG.info("工会数据解析异常，groupInfo:{}", groupInfo);
                throw new BusinessException(BusinessExceptionEnum.GROUP_INFO_PARSING_EXCEPTION.getFailCode(),
                        BusinessExceptionEnum.GROUP_NOT_FOUND_EXCEPTION.getFailReason());
            }
        }
        LOG.info("工会数据 groupInfo:{}", JSON.toJSONString(chatGroup));
        return chatGroup;
    }
}
