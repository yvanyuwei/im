package com.vm.im.service.group.impl;

import com.vm.im.entity.group.ChatGroupFlow;
import com.vm.im.dao.group.ChatGroupFlowMapper;
import com.vm.im.service.group.ChatGroupFlowService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 聊天群人员流动记录表 服务实现类
 * </p>
 *
 * @author zhangqi
 * @since 2019-02-20
 */
@Service
public class ChatGroupFlowServiceImpl extends ServiceImpl<ChatGroupFlowMapper, ChatGroupFlow> implements ChatGroupFlowService {
    private static final Logger LOG = LoggerFactory.getLogger(ChatGroupFlowServiceImpl.class);
}
