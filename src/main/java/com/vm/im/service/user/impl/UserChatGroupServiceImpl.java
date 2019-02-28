package com.vm.im.service.user.impl;

import com.vm.im.entity.user.UserChatGroup;
import com.vm.im.dao.user.UserChatGroupMapper;
import com.vm.im.service.user.UserChatGroupService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户群 服务实现类
 * </p>
 *
 * @author zhangqi
 * @since 2019-02-19
 */
@Service
public class UserChatGroupServiceImpl extends ServiceImpl<UserChatGroupMapper, UserChatGroup> implements UserChatGroupService {
    private static final Logger LOG = LoggerFactory.getLogger(UserChatGroupServiceImpl.class);
}
