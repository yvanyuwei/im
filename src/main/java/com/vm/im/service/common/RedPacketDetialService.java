package com.vm.im.service.common;

import com.vm.im.common.dto.admin.ReceiveRedPacketDTO;
import com.vm.im.entity.common.RedPacketDetial;
import com.baomidou.mybatisplus.extension.service.IService;
import com.vm.im.entity.user.User;

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
     * @return
     */
    RedPacketDetial createRedPacketDetial(ReceiveRedPacketDTO receiveRedPacketDTO, User fromUser);

}
