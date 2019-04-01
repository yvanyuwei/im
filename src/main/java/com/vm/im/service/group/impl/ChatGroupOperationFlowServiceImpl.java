package com.vm.im.service.group.impl;

import com.alibaba.fastjson.JSON;
import com.vm.im.common.constant.CommonConstant;
import com.vm.im.common.constant.GroupOperationConstant;
import com.vm.im.common.enums.AdminRoleEnum;
import com.vm.im.common.enums.GroupRoleEnum;
import com.vm.im.common.util.UUIDUtil;
import com.vm.im.entity.group.ChatGroup;
import com.vm.im.entity.group.ChatGroupOperationFlow;
import com.vm.im.dao.group.ChatGroupOperationFlowMapper;
import com.vm.im.entity.user.UserChatGroup;
import com.vm.im.service.group.ChatGroupOperationFlowService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;

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

    /**
     * 添加删除群组的操作流水
     *
     * @param chatGroup
     */
    @Override
    public void addGroupFlow(ChatGroup chatGroup) {
        LOG.info("开始添加群组的操作流水");
        ChatGroupOperationFlow chatGroupOperationFlow = bulidChatGroupOperationFlow(chatGroup);
        save(chatGroupOperationFlow);
    }

    /**
     * 添加群组用户权限操作流水
     *
     * @param userChatGroup
     */
    @Override
    public void addMemberAuthOperationFlow(UserChatGroup userChatGroup) {
        LOG.info("开始添加群组用户权限操作流水");
        ChatGroupOperationFlow chatGroupOperationFlow = bulidChatGroupOperationFlow(userChatGroup);
        save(chatGroupOperationFlow);
    }

    /**
     * 构建群组用户权限操作流水
     *
     * @param userChatGroup
     * @return
     */
    private ChatGroupOperationFlow bulidChatGroupOperationFlow(UserChatGroup userChatGroup) {
        ChatGroupOperationFlow chatGroupOperationFlow = new ChatGroupOperationFlow();
        chatGroupOperationFlow.setId(UUIDUtil.get32UUID());
        chatGroupOperationFlow.setChatGroupId(userChatGroup.getChatGroupId());
        chatGroupOperationFlow.setOperatorId(AdminRoleEnum.ADMIN.name());
        chatGroupOperationFlow.setCreateTime(new Date());
        String role = GroupRoleEnum.USER.value() == userChatGroup.getType() ? GroupRoleEnum.USER.name() : GroupRoleEnum.ADMIN.name();
        chatGroupOperationFlow.setContent(userChatGroup.getUserId() + GroupOperationConstant.SET_ROLE + role);
        LOG.info("构建群组操作流水, chatGroupOperationFlow:{}", JSON.toJSONString(chatGroupOperationFlow));

        return chatGroupOperationFlow;
    }

    /**
     * 构建群组的操作流水
     *
     * @param chatGroup
     * @return
     */
    private ChatGroupOperationFlow bulidChatGroupOperationFlow(ChatGroup chatGroup) {
        ChatGroupOperationFlow chatGroupOperationFlow = new ChatGroupOperationFlow();
        chatGroupOperationFlow.setId(UUIDUtil.get32UUID());
        chatGroupOperationFlow.setChatGroupId(chatGroup.getId());
        chatGroupOperationFlow.setOperatorId(chatGroup.getMaster());
        chatGroupOperationFlow.setCreateTime(new Date());
        String content = chatGroup.getDelFlag() == CommonConstant.YES ? GroupOperationConstant.DELETE_GROUP : GroupOperationConstant.CREATE_GROUP;
        chatGroupOperationFlow.setContent(content);
        LOG.info("构建群组操作流水, chatGroupOperationFlow:{}", JSON.toJSONString(chatGroupOperationFlow));

        return chatGroupOperationFlow;
    }
}
