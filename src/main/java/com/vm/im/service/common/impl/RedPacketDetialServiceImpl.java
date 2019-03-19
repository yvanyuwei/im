package com.vm.im.service.common.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.vm.im.common.constant.CommonConstant;
import com.vm.im.common.dto.admin.ReceiveRedPacketDTO;
import com.vm.im.common.enums.AdminRoleEnum;
import com.vm.im.common.enums.BusinessExceptionEnum;
import com.vm.im.common.enums.MessageTypeEnum;
import com.vm.im.common.enums.RedPacketTypeEnum;
import com.vm.im.common.exception.BusinessException;
import com.vm.im.entity.common.RedPacketDetial;
import com.vm.im.dao.common.RedPacketDetialMapper;
import com.vm.im.entity.user.User;
import com.vm.im.netty.Constant;
import com.vm.im.service.Redis.RedisService;
import com.vm.im.service.chat.ChatService;
import com.vm.im.service.common.RedPacketDetialService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 红包明细 服务实现类
 * </p>
 *
 * @author zhangqi
 * @since 2019-02-20
 */
@Service
public class RedPacketDetialServiceImpl extends ServiceImpl<RedPacketDetialMapper, RedPacketDetial> implements RedPacketDetialService {
    private static final Logger LOG = LoggerFactory.getLogger(RedPacketDetialServiceImpl.class);

    @Autowired
    private RedPacketDetialMapper redPacketDetialMapper;

    @Autowired
    private RedisService redisService;

    @Autowired
    private ChatService chatService;

    @Override
    public RedPacketDetial createRedPacketDetial(ReceiveRedPacketDTO receiveRedPacketDTO, User fromUser) {
        RedPacketDetial redPacketDetial = bulidRedPacketDetial(receiveRedPacketDTO);
        boolean save = save(redPacketDetial);
        if (save) {
            LOG.info("保存红包明细信息成功, 将消息发送到kafka, redPacketDetialId:{}", receiveRedPacketDTO.getBusinessId());
            ChannelHandlerContext channelHandlerContext = Constant.onlineUserMap.get(fromUser.getId());
            if (receiveRedPacketDTO.getType().equals(RedPacketTypeEnum.USER.value())) {
                JSONObject param = bulidJsonObject(receiveRedPacketDTO);
                User toUser = redisService.getRedisUserById(receiveRedPacketDTO.getToId());
                chatService.singleSend(param, channelHandlerContext, fromUser, toUser);
            } else {
                JSONObject param = bulidJsonObject(receiveRedPacketDTO);
                chatService.groupSend(param, channelHandlerContext, fromUser);
            }
        } else {
            LOG.info("保存创建红包明细失败, receiveRedPacketDTO:{}", JSON.toJSONString(receiveRedPacketDTO));
            throw new BusinessException(BusinessExceptionEnum.RED_PACKET_EXCEPTION.getFailCode(), BusinessExceptionEnum.RED_PACKET_EXCEPTION.getFailReason());
        }

        return redPacketDetial;
    }

    /**
     * 根据红包Id 查询红包明细
     *
     * @param id
     */
    @Override
    public RedPacketDetial selectByRedPacketId(String id) {
        return redPacketDetialMapper.selectByRedPacketId(id);
    }

    /**
     * 获取指定红包的总金额
     *
     * @param id
     * @return
     */
    @Override
    public BigDecimal sumAmountByRedPacketId(String id) {
        BigDecimal sum = redPacketDetialMapper.sumAmountByRedPacketId(id);
        return sum == null ? BigDecimal.ZERO : sum;
    }

    /**
     * 构建收红包系统消息
     *
     * @param receiveRedPacketDTO
     * @return
     */
    private JSONObject bulidJsonObject(ReceiveRedPacketDTO receiveRedPacketDTO) {
        JSONObject result = new JSONObject();
        result.put("fromUserId", receiveRedPacketDTO.getFromId());
        result.put("toUserId", receiveRedPacketDTO.getToId());
        result.put("content", JSON.toJSONString(receiveRedPacketDTO));
        result.put("msgType", MessageTypeEnum.SYSTEM_MSG);
        result.put("role", AdminRoleEnum.ADMIN.name());

        LOG.info("构建收红包系统消息, result:{}", result.toString());
        return result;
    }


    /**
     * 构建红包明细
     *
     * @param receiveRedPacketDTO
     * @return
     */
    private RedPacketDetial bulidRedPacketDetial(ReceiveRedPacketDTO receiveRedPacketDTO) {
        RedPacketDetial redPacketDetial = new RedPacketDetial();
        redPacketDetial.setId(receiveRedPacketDTO.getBusinessId());
        redPacketDetial.setRedPacketId(receiveRedPacketDTO.getRedPacketId());
        redPacketDetial.setFromId(receiveRedPacketDTO.getFromId());
        redPacketDetial.setToId(receiveRedPacketDTO.getToId());
        redPacketDetial.setType(receiveRedPacketDTO.getType());
        redPacketDetial.setCoinId(receiveRedPacketDTO.getCoinId());
        redPacketDetial.setCoinName(receiveRedPacketDTO.getCoinName());
        redPacketDetial.setAmount(receiveRedPacketDTO.getAmount());
        redPacketDetial.setStatus(CommonConstant.YES);
        redPacketDetial.setCreateTime(new Date());

        LOG.info("构建红包明细成功, redPacketDetial:{}", JSON.toJSONString(redPacketDetial));
        return redPacketDetial;
    }
}
