package com.vm.im.service.Redis;

import com.vm.im.entity.group.ChatGroup;
import com.vm.im.entity.user.User;

public interface RedisService {

    /**
     * 到redis 查询用户信息 如果找不到到数据库查询 并存入redis
     *
     * @param userId
     * @return
     */
    User getRedisUserById(String userId);

    /**
     * 到redis 查询工会信息 如果找不到到数据库查询 并存入redis
     * @param groupId
     * @return
     */
    ChatGroup getRedisGroupMsgByGId(String groupId);
}
