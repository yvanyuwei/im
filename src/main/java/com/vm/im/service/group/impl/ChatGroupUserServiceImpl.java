package com.vm.im.service.group.impl;

import com.vm.im.entity.group.ChatGroupUser;
import com.vm.im.dao.group.ChatGroupUserMapper;
import com.vm.im.service.group.ChatGroupUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 群成员表 服务实现类
 * </p>
 *
 * @author zhangqi
 * @since 2019-02-20
 */
@Service
public class ChatGroupUserServiceImpl extends ServiceImpl<ChatGroupUserMapper, ChatGroupUser> implements ChatGroupUserService {
    private static final Logger LOG = LoggerFactory.getLogger(ChatGroupUserServiceImpl.class);
}
