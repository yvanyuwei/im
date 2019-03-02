package com.vm.im.service.user.impl;

import com.vm.im.entity.user.UserCurrentChat;
import com.vm.im.dao.user.UserCurrentChatMapper;
import com.vm.im.service.user.UserCurrentChatService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户当前会话 服务实现类
 * </p>
 *
 * @author zhangqi
 * @since 2019-03-02
 */
@Service
public class UserCurrentChatServiceImpl extends ServiceImpl<UserCurrentChatMapper, UserCurrentChat> implements UserCurrentChatService {
    private static final Logger LOG = LoggerFactory.getLogger(UserCurrentChatServiceImpl.class);

}
