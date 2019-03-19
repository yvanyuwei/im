package com.vm.im.service.common;

import com.vm.im.common.dto.admin.GiveRedPacketDTO;
import com.vm.im.entity.common.RedPacket;
import com.baomidou.mybatisplus.extension.service.IService;
import com.vm.im.entity.user.User;

/**
 * <p>
 * 红包 服务类
 * </p>
 *
 * @author zhangqi
 * @since 2019-02-20
 */
public interface RedPacketService extends IService<RedPacket> {

    /**
     * 创建红包(保存数据发送消息到kafka)
     *
     * @param giveRedPacketDTO
     * @param fromUser
     * @return
     */
    RedPacket createRedPacket(GiveRedPacketDTO giveRedPacketDTO, User fromUser);

}
