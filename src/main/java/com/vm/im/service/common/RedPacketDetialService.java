package com.vm.im.service.common;

import com.vm.im.common.dto.admin.ReceiveRedPacketDTO;
import com.vm.im.entity.common.RedPacketDetial;
import com.baomidou.mybatisplus.extension.service.IService;
import com.vm.im.entity.group.ChatGroup;
import com.vm.im.entity.user.User;

import java.math.BigDecimal;

/**
 * <p>
 * 红包明细 服务类
 * </p>
 *
 * @author zhangqi
 * @since 2019-02-20
 */
public interface RedPacketDetialService extends IService<RedPacketDetial> {

    /**
     * 创建红包明细
     *
     * @param receiveRedPacketDTO
     * @param fromUser
     * @param toUser
     * @param chatGroup
     * @return
     */
    RedPacketDetial createRedPacketDetial(ReceiveRedPacketDTO receiveRedPacketDTO, User fromUser, User toUser, ChatGroup chatGroup);

    /**
     * 根据红包Id 查询红包明细
     *
     * @param id
     */
    RedPacketDetial selectByRedPacketId(String id);

    /**
     * 获取指定红包的总金额
     *
     * @param id
     * @return
     */
    BigDecimal sumAmountByRedPacketId(String id);
}
