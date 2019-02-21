package com.vm.im.service.user.impl;

import com.vm.im.entity.user.UserFriend;
import com.vm.im.dao.user.UserFriendMapper;
import com.vm.im.service.user.UserFriendService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户好友 服务实现类
 * </p>
 *
 * @author zhangqi
 * @since 2019-02-19
 */
@Service
public class UserFriendServiceImpl extends ServiceImpl<UserFriendMapper, UserFriend> implements UserFriendService {
    private static final Logger LOG = LoggerFactory.getLogger(UserFriendServiceImpl.class);
}
