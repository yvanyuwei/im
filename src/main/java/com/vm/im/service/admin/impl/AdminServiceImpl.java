package com.vm.im.service.admin.impl;

import com.vm.im.common.constant.CommonConstant;
import com.vm.im.common.dto.admin.GiveRedPacketDTO;
import com.vm.im.common.enums.BusinessExceptionEnum;
import com.vm.im.common.exception.BusinessException;
import com.vm.im.entity.common.RedPacket;
import com.vm.im.entity.group.ChatGroup;
import com.vm.im.entity.user.User;
import com.vm.im.service.admin.AdminService;
import com.vm.im.service.common.RedPacketService;
import com.vm.im.service.group.ChatGroupService;
import com.vm.im.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName: AdminServiceImpl
 * @Description: 管理账号内部通讯服务
 * @Author zhangqi
 * @Date 2019年03月13日14时06分
 * @Version 1.0
 */
@Service
public class AdminServiceImpl implements AdminService {
    private static final Logger LOG = LoggerFactory.getLogger(AdminServiceImpl.class);

    @Autowired
    private UserService userService;

    @Autowired
    private RedPacketService redPacketService;

    @Autowired
    private ChatGroupService chatGroupService;

    /**
     * 发送个人红包
     *
     * @param fromUser
     * @param giveRedPacketDTO
     */
    @Override
    public void giveUserRedPacket(User fromUser, GiveRedPacketDTO giveRedPacketDTO) {
        User toUser = userService.getRedisUserById(giveRedPacketDTO.getToId());
        if (toUser == null || toUser.getDelFlag() == CommonConstant.YES){
            LOG.info("用户不存在,或者状态为不可用, toUserId:{}", giveRedPacketDTO.getToId());
            throw new BusinessException(BusinessExceptionEnum.USER_NOT_EXIST_EXCEPTION.getFailCode(), BusinessExceptionEnum.USER_NOT_EXIST_EXCEPTION.getFailReason());
        }

        RedPacket redPacket = redPacketService.createRedPacket(giveRedPacketDTO);
    }

    /**
     * 发送群红包
     *
     * @param fromUser
     * @param giveRedPacketDTO
     */
    @Override
    public void giveGroupRedPacket(User fromUser, GiveRedPacketDTO giveRedPacketDTO) {
        ChatGroup chatGroup = chatGroupService.getById(giveRedPacketDTO.getId());
        if (chatGroup == null || chatGroup.getDelFlag() == CommonConstant.YES){
            LOG.info("群组不存在,或者状态为不可用, chatGroupId:{}", giveRedPacketDTO.getToId());
            throw new BusinessException(BusinessExceptionEnum.GROUP_NOT_FOUND_EXCEPTION.getFailCode(), BusinessExceptionEnum.GROUP_NOT_FOUND_EXCEPTION.getFailReason());
        }

        RedPacket redPacket = redPacketService.createRedPacket(giveRedPacketDTO);
    }

    /**
     * 校验红包是否存在
     * 存在就返回,不存在返回null
     *
     * @param id
     * @return
     */
    @Override
    public RedPacket checkRedPacket(String id) {
        return redPacketService.getById(id);
    }
}
