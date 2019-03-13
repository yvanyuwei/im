package com.vm.im.service.common;

import com.vm.im.common.dto.admin.GiveRedPacketDTO;
import com.vm.im.entity.common.RedPacket;
import com.baomidou.mybatisplus.extension.service.IService;

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
     * @return
     */
    RedPacket createRedPacket(GiveRedPacketDTO giveRedPacketDTO);

}
