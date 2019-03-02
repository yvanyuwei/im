package com.vm.im.service.user.impl;

import com.vm.im.entity.user.UserCurrentChat;
import com.vm.im.dao.user.UserCurrentChatMapper;
import com.vm.im.service.user.UserCurrentChatService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Autowired
    private UserCurrentChatMapper userCurrentChatMapper;

    /**
     * 根据用户id 查询当前会话列表
     *
     * @param uid   用户id
     * @param count list size
     * @return
     */
    @Override
    public List<UserCurrentChat> listByUid(String uid, int count) {
        return userCurrentChatMapper.listByUid(uid, count);
    }
}
