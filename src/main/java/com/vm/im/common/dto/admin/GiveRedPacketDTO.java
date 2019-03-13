package com.vm.im.common.dto.admin;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @ClassName: GiveRedPacketDTO
 * @Description: 发红包DTO
 * @Author zhangqi
 * @Date 2019年03月12日15时46分
 * @Version 1.0
 */
@Data
public class GiveRedPacketDTO {

    /**
     * 红包id
     */
    private String id;

    /**
     * 发送人id
     */
    private String fromId;

    /**
     * 接收人id/接收群id
     */
    private String toId;

    /**
     * 类型 1: 个人红包, 3：群红包 5: 工会红包
     */
    private Integer type;

    /**
     * 币种
     */
    private String coinId;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 个数
     */
    private Integer number;

    /**
     * 说明
     */
    private String remarks;

    /**
     * 状态 -1： 无效的, 0: 失败的, 1成功
     */
    private Integer status;

    private Date createTime;

    private Date updateTime;
}
