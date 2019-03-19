package com.vm.im.service.common.impl;

import com.alibaba.fastjson.JSON;
import com.vm.im.common.constant.CommonConstant;
import com.vm.im.common.dto.admin.GiveRedPacketDTO;
import com.vm.im.common.enums.BusinessExceptionEnum;
import com.vm.im.common.enums.RedPacketTypeEnum;
import com.vm.im.common.exception.BusinessException;
import com.vm.im.entity.common.RedPacket;
import com.vm.im.dao.common.RedPacketMapper;
import com.vm.im.netty.MyWebSocketServerHandler;
import com.vm.im.service.chat.ChatService;
import com.vm.im.service.common.RedPacketService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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

    /**
     * 创建红包(保存数据发送消息到kafka)
     *
     * @param giveRedPacketDTO
     * @return
     */
    @Override
    public RedPacket createRedPacket(GiveRedPacketDTO giveRedPacketDTO) {
        RedPacket redPacket = buildRedPacket(giveRedPacketDTO);
        boolean save = save(redPacket);
        if (save){
            LOG.info("保存红包信息成功, 将消息发送到kafka, redPacketId:{}", redPacket.getId());
            if (giveRedPacketDTO.getType().equals(RedPacketTypeEnum.USER.value())){
//                chatService.singleSend();
            }else {
//                chatService.groupSend();
            }
        }else {
            LOG.info("保存创建红包失败, redPacket:{}", JSON.toJSONString(redPacket));
            throw new BusinessException(BusinessExceptionEnum.RED_PACKET_EXCEPTION.getFailCode(), BusinessExceptionEnum.RED_PACKET_EXCEPTION.getFailReason());
        }
        return redPacket;
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
