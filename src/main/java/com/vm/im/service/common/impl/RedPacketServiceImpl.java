package com.vm.im.service.common.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.vm.im.common.constant.CommonConstant;
import com.vm.im.common.dto.admin.GiveRedPacketDTO;
import com.vm.im.common.enums.AdminRoleEnum;
import com.vm.im.common.enums.BusinessExceptionEnum;
import com.vm.im.common.enums.MessageTypeEnum;
import com.vm.im.common.enums.RedPacketTypeEnum;
import com.vm.im.common.exception.BusinessException;
import com.vm.im.entity.common.RedPacket;
import com.vm.im.dao.common.RedPacketMapper;
import com.vm.im.entity.user.User;
import com.vm.im.netty.Constant;
import com.vm.im.service.Redis.RedisService;
import com.vm.im.service.chat.ChatService;
import com.vm.im.service.common.RedPacketService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 * 红包 服务实现类
 * </p>
 *
 * @author zhangqi
 * @since 2019-02-20
 */
@Service
public class RedPacketServiceImpl extends ServiceImpl<RedPacketMapper, RedPacket> implements RedPacketService {
    private static final Logger LOG = LoggerFactory.getLogger(RedPacketServiceImpl.class);

    @Autowired
    private ChatService chatService;

    @Autowired
    private RedisService redisService;

    /**
     * 创建红包(保存数据发送消息到kafka)
     *
     * @param giveRedPacketDTO
     * @param fromUser
     * @return
     */
    @Override
    public RedPacket createRedPacket(GiveRedPacketDTO giveRedPacketDTO, User fromUser) {
        RedPacket redPacket = buildRedPacket(giveRedPacketDTO);
        boolean save = save(redPacket);
        if (save) {
            LOG.info("保存红包信息成功, 将消息发送到kafka, redPacketId:{}", redPacket.getId());
            ChannelHandlerContext channelHandlerContext = Constant.onlineUserMap.get(fromUser.getId());
            if (giveRedPacketDTO.getType().equals(RedPacketTypeEnum.USER.value())) {
                JSONObject param = bulidJsonObject(giveRedPacketDTO);
                User toUser = redisService.getRedisUserById(giveRedPacketDTO.getToId());
                try {
                    chatService.singleSend(param,channelHandlerContext , fromUser, toUser);
                } catch (Exception e) {
                    LOG.info("发送个人消息异常, param:{}", param);
                    // TODO 异常未做处理
                }
            } else {
                JSONObject param = bulidJsonObject(giveRedPacketDTO);
                try {
                    chatService.groupSend(param,channelHandlerContext , fromUser);
                } catch (Exception e) {
                    LOG.info("发送群组消息异常, param:{}", param);
                }
            }
        } else {
            LOG.info("保存创建红包失败, redPacket:{}", JSON.toJSONString(redPacket));
            throw new BusinessException(BusinessExceptionEnum.RED_PACKET_EXCEPTION.getFailCode(), BusinessExceptionEnum.RED_PACKET_EXCEPTION.getFailReason());
        }
        return redPacket;
    }

    /**
     * 构建发红包消息
     *
     * @param giveRedPacketDTO
     * @return
     */
    private JSONObject bulidJsonObject(GiveRedPacketDTO giveRedPacketDTO) {
        JSONObject result = new JSONObject();
        result.put("fromUserId", giveRedPacketDTO.getFromId());

        if (giveRedPacketDTO.getType().equals(RedPacketTypeEnum.USER.value())){
            result.put("toUserId", giveRedPacketDTO.getToId());
        }else {
            result.put("toGroupId", giveRedPacketDTO.getToId());
        }
        result.put("content", JSON.toJSONString(giveRedPacketDTO));
        result.put("msgType", MessageTypeEnum.RED_PACKET_MSG);
        result.put("role", AdminRoleEnum.ADMIN.name());

        LOG.info("构建收红包系统消息, result:{}", result.toString());
        return result;
    }

    /**
     * 构建红包信息
     *
     * @param giveRedPacketDTO
     * @return
     */
    private RedPacket buildRedPacket(GiveRedPacketDTO giveRedPacketDTO) {
        RedPacket redPacket = new RedPacket();
        redPacket.setId(giveRedPacketDTO.getId());
        redPacket.setFromId(giveRedPacketDTO.getFromId());
        redPacket.setToId(giveRedPacketDTO.getToId());
        redPacket.setType(giveRedPacketDTO.getType());
        redPacket.setCoinId(giveRedPacketDTO.getCoinId());
        redPacket.setCoinName(giveRedPacketDTO.getCoinName());
        redPacket.setAmount(giveRedPacketDTO.getAmount());
        redPacket.setNumber(giveRedPacketDTO.getNumber());
        redPacket.setRemarks(giveRedPacketDTO.getRemarks());
        redPacket.setStatus(CommonConstant.YES);
        redPacket.setCreateTime(new Date());

        LOG.info("构建红包信息成功, redPacket:{}", JSON.toJSONString(redPacket));
        return redPacket;
    }
}
