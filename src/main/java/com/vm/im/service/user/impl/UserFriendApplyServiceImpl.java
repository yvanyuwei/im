package com.vm.im.service.user.impl;

import com.vm.im.entity.user.UserFriendApply;
import com.vm.im.dao.user.UserFriendApplyMapper;
import com.vm.im.service.user.UserFriendApplyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户好友申请 服务实现类
 * </p>
 *
 * @author zhangqi
 * @since 2019-02-19
 */
@Service
public class UserFriendApplyServiceImpl extends ServiceImpl<UserFriendApplyMapper, UserFriendApply> implements UserFriendApplyService {
    private static final Logger LOG = LoggerFactory.getLogger(UserFriendApplyServiceImpl.class);
}
