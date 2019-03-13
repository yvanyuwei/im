package com.vm.im.service.group.impl;

import com.vm.im.entity.group.ChatGroupApply;
import com.vm.im.dao.group.ChatGroupApplyMapper;
import com.vm.im.service.group.ChatGroupApplyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 聊天群邀请/申请 服务实现类
 * </p>
 *
 * @author zhangqi
 * @since 2019-02-20
 */
@Service
public class ChatGroupApplyServiceImpl extends ServiceImpl<ChatGroupApplyMapper, ChatGroupApply> implements ChatGroupApplyService {
    private static final Logger LOG = LoggerFactory.getLogger(ChatGroupApplyServiceImpl.class);
}
