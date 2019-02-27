package com.vm.im.service.group.impl;

import com.vm.im.common.dto.admin.UnionOperationDTO;
import com.vm.im.common.enums.BusinessExceptionEnum;
import com.vm.im.common.exception.BusinessException;
import com.vm.im.entity.group.ChatGroup;
import com.vm.im.dao.group.ChatGroupMapper;
import com.vm.im.service.group.ChatGroupService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 聊天群 服务实现类
 * </p>
 *
 * @author zhangqi
 * @since 2019-02-20
 */
@Service
public class ChatGroupServiceImpl extends ServiceImpl<ChatGroupMapper, ChatGroup> implements ChatGroupService {
    private static final Logger LOG = LoggerFactory.getLogger(ChatGroupServiceImpl.class);

    @Override
    public ChatGroup checkGroup(UnionOperationDTO unionOperationDTO) {
        ChatGroup group = getById(unionOperationDTO.getGroupId());
        if (group == null){
            throw  new BusinessException(BusinessExceptionEnum.GROUP_NOT_FOUND_EXCEPTION.getFailCode(), BusinessExceptionEnum.GROUP_NOT_FOUND_EXCEPTION.getFailReason());
        }

        return group;
    }
}
