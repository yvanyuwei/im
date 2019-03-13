package com.vm.im.service.group;

import com.vm.im.entity.group.ChatGroup;
import com.vm.im.entity.group.ChatGroupOperationFlow;
import com.baomidou.mybatisplus.extension.service.IService;
import com.vm.im.entity.user.UserChatGroup;

/**
 * <p>
 * 聊天群操作流水记录表 服务类
 * </p>
 *
 * @author zhangqi
 * @since 2019-02-20
 */
public interface ChatGroupOperationFlowService extends IService<ChatGroupOperationFlow> {

    /**
     * 添加删除群组的操作流水
     *
     * @param chatGroup
     */
    void addDeleteGroupFlow(ChatGroup chatGroup);

    /**
     * 添加群组用户权限操作流水
     *
     * @param userChatGroup
     */
    void addMemberAuthOperationFlow(UserChatGroup userChatGroup);
}
