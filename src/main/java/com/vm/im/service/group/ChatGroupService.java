package com.vm.im.service.group;

import com.vm.im.common.dto.admin.UnionOperationDTO;
import com.vm.im.entity.group.ChatGroup;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 聊天群 服务类
 * </p>
 *
 * @author zhangqi
 * @since 2019-02-20
 */
public interface ChatGroupService extends IService<ChatGroup> {

    /**
     * 校验聊天群状态如果不存在则抛出异常
     *
     * @param unionOperationDTO
     * @return
     */
    ChatGroup checkGroup(UnionOperationDTO unionOperationDTO);
}
