package com.vm.im.service.group.impl;

import com.alibaba.fastjson.JSON;
import com.vm.im.common.constant.CommonConstant;
import com.vm.im.common.enums.AdminRoleEnum;
import com.vm.im.common.enums.ChatGroupFlowTypeEnum;
import com.vm.im.common.util.UUIDUtil;
import com.vm.im.entity.group.ChatGroup;
import com.vm.im.entity.group.ChatGroupFlow;
import com.vm.im.dao.group.ChatGroupFlowMapper;
import com.vm.im.entity.user.UserChatGroup;
import com.vm.im.service.group.ChatGroupFlowService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;

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

    /**
     * 添加群主加入流水
     *
     * @param chatGroup
     */
    @Override
    public void addGroupMaster(ChatGroup chatGroup) {
        LOG.info("开始保存群组人员流水");
        ChatGroupFlow chatGroupFlow = buildChatGroupMasterFlow(chatGroup);
        save(chatGroupFlow);
    }

    /**
     * 添加成员加入流水
     *
     * @param userChatGroup
     */
    @Override
    public void addChatGroupFlow(UserChatGroup userChatGroup) {
        LOG.info("开始保存群组人员流水");
        ChatGroupFlow chatGroupFlow = buildChatGroupFlow(userChatGroup);
        save(chatGroupFlow);
    }

    /**
     * 构建用户群组流水
     *
     * @param userChatGroup
     * @return
     */
    private ChatGroupFlow buildChatGroupFlow(UserChatGroup userChatGroup) {
        ChatGroupFlow chatGroupFlow = new ChatGroupFlow();
        chatGroupFlow.setId(UUIDUtil.get32UUID());
        chatGroupFlow.setChatGroupId(userChatGroup.getChatGroupId());
        chatGroupFlow.setUserId(userChatGroup.getUserId());
        chatGroupFlow.setCreateTime(new Date());
        int type = CommonConstant.YES == userChatGroup.getDelFlag() ? ChatGroupFlowTypeEnum.QUIT.value() : ChatGroupFlowTypeEnum.APPLY.value();
        chatGroupFlow.setType(type);
        LOG.info("构建用户群组流水, chatGroupFlow:{}", JSON.toJSONString(chatGroupFlow));

        return chatGroupFlow;
    }

    /**
     * 构建群主流水
     *
     * @param chatGroup
     * @return
     */
    private ChatGroupFlow buildChatGroupMasterFlow(ChatGroup chatGroup) {
        ChatGroupFlow chatGroupFlow = new ChatGroupFlow();
        chatGroupFlow.setId(UUIDUtil.get32UUID());
        chatGroupFlow.setChatGroupId(chatGroup.getId());
        chatGroupFlow.setUserId(chatGroup.getMaster());
        chatGroupFlow.setCreateTime(new Date());
        chatGroupFlow.setOperatorId(AdminRoleEnum.ADMIN.name());
        chatGroupFlow.setType(ChatGroupFlowTypeEnum.APPLY.value());
        LOG.info("构建群组群主流水, chatGroupFlow:{}", JSON.toJSONString(chatGroupFlow));

        return chatGroupFlow;
    }
}
