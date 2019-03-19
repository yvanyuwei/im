package com.vm.im.service.admin;


import com.vm.im.common.dto.admin.GiveRedPacketDTO;
import com.vm.im.common.dto.admin.ReceiveRedPacketDTO;
import com.vm.im.entity.common.RedPacket;
import com.vm.im.entity.common.RedPacketDetial;
import com.vm.im.entity.user.User;

public interface AdminService {


    /**
     * 发送个人红包
     *
     * @param fromUser
     * @param giveRedPacketDTO
     */
    void giveUserRedPacket(User fromUser, GiveRedPacketDTO giveRedPacketDTO);

    /**
     * 发送群红包
     *
     * @param fromUser
     * @param giveRedPacketDTO
     */
    void giveGroupRedPacket(User fromUser, GiveRedPacketDTO giveRedPacketDTO);

    /**
     * 校验红包是否存在
     * 存在就返回,不存在返回null
     *
     * @param id
     * @return
     */
    RedPacket checkRedPacket(String id);


    /**
     * 更新红包状态
     *
     * @param redPacket
     */
    void updateRedPacket(RedPacket redPacket);

    /**
     * 收到个人红包
     *
     * @param fromUser
     * @param receiveRedPacketDTO
     */
    void receiveUserRedPacket(User fromUser, ReceiveRedPacketDTO receiveRedPacketDTO);

    /**
     * 收到群组红包
     *  @param fromUser
     * @param redPacket
     * @param receiveRedPacketDTO
     */
    void receiveGroupRedPacket(User fromUser, RedPacket redPacket, ReceiveRedPacketDTO receiveRedPacketDTO);

    /**
     * 校验红包明细是否存在
     *
     * @param businessId
     * @return
     */
    RedPacketDetial checkRedPacketDetial(String businessId);
}
