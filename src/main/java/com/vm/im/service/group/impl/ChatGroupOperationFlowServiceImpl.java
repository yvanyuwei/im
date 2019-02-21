package com.vm.im.service.group.impl;

import com.vm.im.entity.group.ChatGroupOperationFlow;
import com.vm.im.dao.group.ChatGroupOperationFlowMapper;
import com.vm.im.service.group.ChatGroupOperationFlowService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 聊天群操作流水记录表 服务实现类
 * </p>
 *
 * @author zhangqi
 * @since 2019-02-20
 */
@Service
public class ChatGroupOperationFlowServiceImpl extends ServiceImpl<ChatGroupOperationFlowMapper, ChatGroupOperationFlow> implements ChatGroupOperationFlowService {
    private static final Logger LOG = LoggerFactory.getLogger(ChatGroupOperationFlowServiceImpl.class);
}
