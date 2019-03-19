package com.vm.im.service.common.impl;

import com.alibaba.fastjson.JSON;
import com.vm.im.common.constant.CommonConstant;
import com.vm.im.common.dto.admin.ReceiveRedPacketDTO;
import com.vm.im.common.enums.BusinessExceptionEnum;
import com.vm.im.common.enums.RedPacketTypeEnum;
import com.vm.im.common.exception.BusinessException;
import com.vm.im.entity.common.RedPacketDetial;
import com.vm.im.dao.common.RedPacketDetialMapper;
import com.vm.im.service.common.RedPacketDetialService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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

    @Override
    public RedPacketDetial createRedPacketDetial(ReceiveRedPacketDTO receiveRedPacketDTO) {
        RedPacketDetial redPacketDetial = bulidRedPacketDetial(receiveRedPacketDTO);
        boolean save = save(redPacketDetial);
        if (save){
            LOG.info("保存红包明细信息成功, 将消息发送到kafka, redPacketDetialId:{}", receiveRedPacketDTO.getBusinessId());
            if (receiveRedPacketDTO.getType().equals(RedPacketTypeEnum.USER.value())){
//                chatService.singleSend();
            }else {
//                chatService.groupSend();
            }
        }else {
            LOG.info("保存创建红包明细失败, receiveRedPacketDTO:{}", JSON.toJSONString(receiveRedPacketDTO));
            throw new BusinessException(BusinessExceptionEnum.RED_PACKET_EXCEPTION.getFailCode(), BusinessExceptionEnum.RED_PACKET_EXCEPTION.getFailReason());
        }

        return redPacketDetial;
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
        redPacketDetial.setCreateTime(new Date(receiveRedPacketDTO.getCreateTime()));
        redPacketDetial.setUpdateTime(new Date());

        LOG.info("构建红包明细成功, redPacketDetial:{}", JSON.toJSONString(redPacketDetial));
        return redPacketDetial;
    }
}
