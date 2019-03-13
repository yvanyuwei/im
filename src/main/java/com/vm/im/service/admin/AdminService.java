package com.vm.im.service.admin;


import com.vm.im.common.dto.admin.GiveRedPacketDTO;
import com.vm.im.entity.common.RedPacket;
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
}
