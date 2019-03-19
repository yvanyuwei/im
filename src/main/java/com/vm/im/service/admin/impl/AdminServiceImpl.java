package com.vm.im.service.admin.impl;

import com.alibaba.fastjson.JSON;
import com.vm.im.common.constant.CommonConstant;
import com.vm.im.common.dto.admin.GiveRedPacketDTO;
import com.vm.im.common.dto.admin.ReceiveRedPacketDTO;
import com.vm.im.common.enums.BusinessExceptionEnum;
import com.vm.im.common.enums.RedPacketTypeEnum;
import com.vm.im.common.exception.BusinessException;
import com.vm.im.entity.common.RedPacket;
import com.vm.im.entity.common.RedPacketDetial;
import com.vm.im.entity.group.ChatGroup;
import com.vm.im.entity.user.User;
import com.vm.im.entity.user.UserChatGroup;
import com.vm.im.service.Redis.RedisService;
import com.vm.im.service.admin.AdminService;
import com.vm.im.service.common.RedPacketDetialService;
import com.vm.im.service.common.RedPacketService;
import com.vm.im.service.group.ChatGroupService;
import com.vm.im.service.user.UserChatGroupService;
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

    @Autowired
    private UserChatGroupService userChatGroupService;

    @Autowired
    private RedPacketDetialService redPacketDetialService;

    @Autowired
    private RedisService redisService;
    /**
     * 发送个人红包
     *
     * @param fromUser
     * @param giveRedPacketDTO
     */
    @Override
    public void giveUserRedPacket(User fromUser, GiveRedPacketDTO giveRedPacketDTO) {
        User toUser = redisService.getRedisUserById(giveRedPacketDTO.getToId());
        if (toUser == null || toUser.getDelFlag() == CommonConstant.YES){
            LOG.info("用户不存在,或者状态为不可用, toUserId:{}", giveRedPacketDTO.getToId());
            throw new BusinessException(BusinessExceptionEnum.USER_NOT_EXIST_EXCEPTION.getFailCode(), BusinessExceptionEnum.USER_NOT_EXIST_EXCEPTION.getFailReason());
        }

        RedPacket redPacket = redPacketService.createRedPacket(giveRedPacketDTO, fromUser);
    }

    /**
     * 发送群红包
     *
     * @param fromUser
     * @param giveRedPacketDTO
     */
    @Override
    public void giveGroupRedPacket(User fromUser, GiveRedPacketDTO giveRedPacketDTO) {
        ChatGroup chatGroup = chatGroupService.getById(giveRedPacketDTO.getToId());
        if (chatGroup == null || chatGroup.getDelFlag() == CommonConstant.YES){
            LOG.info("群组不存在,或者状态为不可用, chatGroupId:{}", giveRedPacketDTO.getToId());
            throw new BusinessException(BusinessExceptionEnum.GROUP_NOT_FOUND_EXCEPTION.getFailCode(), BusinessExceptionEnum.GROUP_NOT_FOUND_EXCEPTION.getFailReason());
        }

        UserChatGroup userChatGroup = userChatGroupService.selectUserByGroupIdAndUid(giveRedPacketDTO.getToId(), giveRedPacketDTO.getFromId());
        if (userChatGroup == null || userChatGroup.getDelFlag() == CommonConstant.YES){
            LOG.info("用户未加入该群组, chatGroupId:{}", giveRedPacketDTO.getToId());
            throw new BusinessException(BusinessExceptionEnum.GROUP_MEMBER_NOT_EXIST_EXCEPTION.getFailCode(), BusinessExceptionEnum.GROUP_MEMBER_NOT_EXIST_EXCEPTION.getFailReason());
        }

        RedPacket redPacket = redPacketService.createRedPacket(giveRedPacketDTO, fromUser);
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

    /**
     * 更新红包状态
     *
     * @param redPacket
     */
    @Override
    public void updateRedPacket(RedPacket redPacket) {
        redPacketService.updateById(redPacket);
    }

    /**
     * 收到个人红包
     *  @param fromUser
     * @param redPacket
     * @param receiveRedPacketDTO
     */
    @Override
    public void receiveUserRedPacket(User fromUser, RedPacket redPacket, ReceiveRedPacketDTO receiveRedPacketDTO) {
        User toUser = redisService.getRedisUserById(receiveRedPacketDTO.getToId());
        if (toUser == null || toUser.getDelFlag() == CommonConstant.YES){
            LOG.info("用户不存在,或者状态为不可用, toUserId:{}", receiveRedPacketDTO.getToId());
            throw new BusinessException(BusinessExceptionEnum.USER_NOT_EXIST_EXCEPTION.getFailCode(), BusinessExceptionEnum.USER_NOT_EXIST_EXCEPTION.getFailReason());
        }

        if (redPacket.getToId().equals(receiveRedPacketDTO.getToId())){
            RedPacketDetial redPacketDetial = redPacketDetialService.createRedPacketDetial(receiveRedPacketDTO, fromUser);
        }else {
            LOG.info("红包toId 与 收红包的toId 不符");
            throw new BusinessException(BusinessExceptionEnum.RED_PACKET_EXCEPTION.getFailCode(), BusinessExceptionEnum.RED_PACKET_EXCEPTION.getFailReason());
        }

    }

    /**
     * 收到群组红包
     *  @param fromUser
     * @param redPacket
     * @param receiveRedPacketDTO
     */
    @Override
    public void receiveGroupRedPacket(User fromUser, RedPacket redPacket, ReceiveRedPacketDTO receiveRedPacketDTO) {
        ChatGroup chatGroup = chatGroupService.getById(receiveRedPacketDTO.getToId());
        if (chatGroup == null || chatGroup.getDelFlag() == CommonConstant.YES){
            LOG.info("群组不存在,或者状态为不可用, chatGroupId:{}", receiveRedPacketDTO.getToId());
            throw new BusinessException(BusinessExceptionEnum.GROUP_NOT_FOUND_EXCEPTION.getFailCode(), BusinessExceptionEnum.GROUP_NOT_FOUND_EXCEPTION.getFailReason());
        }

        if (redPacket.getType() == RedPacketTypeEnum.USER.value()){
            LOG.info("该红包不是群组类型红包, redPacketId:{}", JSON.toJSONString(redPacket));
            throw new BusinessException(BusinessExceptionEnum.RED_PACKET_TYPE_EXCEPTION.getFailCode(), BusinessExceptionEnum.RED_PACKET_TYPE_EXCEPTION.getFailReason());
        }

        UserChatGroup userChatGroup = userChatGroupService.selectUserByGroupIdAndUid(redPacket.getToId(), receiveRedPacketDTO.getToId());
        if (userChatGroup == null || userChatGroup.getDelFlag() == CommonConstant.YES){
            LOG.info("用户未加入该群组, chatGroupId:{}", receiveRedPacketDTO.getToId());
            throw new BusinessException(BusinessExceptionEnum.GROUP_MEMBER_NOT_EXIST_EXCEPTION.getFailCode(), BusinessExceptionEnum.GROUP_MEMBER_NOT_EXIST_EXCEPTION.getFailReason());
        }

        RedPacketDetial redPacketDetial = redPacketDetialService.createRedPacketDetial(receiveRedPacketDTO, fromUser);

    }

    /**
     * 校验红包明细是否存在
     *
     * @param businessId
     * @return
     */
    @Override
    public RedPacketDetial checkRedPacketDetial(String businessId) {
        return redPacketDetialService.getById(businessId);
    }
}
