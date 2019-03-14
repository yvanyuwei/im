package com.vm.im.entity.common;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;
import java.io.Serializable;

/**
 * <p>
 * 红包明细
 * </p>
 *
 * @author zhangqi
 * @since 2019-03-13
 */
@TableName("im_red_packet_detial")
public class RedPacketDetial implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 红包id
     */
    private String redPacketId;

    /**
     * 发送人id
     */
    private String fromId;

    /**
     * 接收人id
     */
    private String toId;

    /**
     * 类型 1: 个人红包, 3：群红包
     */
    private Integer type;

    /**
     * 币种
     */
    private String coinId;

    /**
     * 币种名称
     */
    private String coinName;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 状态 -1： 无效的, 0: 失败的, 1成功
     */
    private Integer status;

    private Date createTime;

    private Date updateTime;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRedPacketId() {
        return redPacketId;
    }

    public void setRedPacketId(String redPacketId) {
        this.redPacketId = redPacketId;
    }

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    public String getToId() {
        return toId;
    }

    public void setToId(String toId) {
        this.toId = toId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getCoinId() {
        return coinId;
    }

    public void setCoinId(String coinId) {
        this.coinId = coinId;
    }

    public String getCoinName() {
        return coinName;
    }

    public void setCoinName(String coinName) {
        this.coinName = coinName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "RedPacketDetial{" +
                "id=" + id +
                ", redPacketId=" + redPacketId +
                ", fromId=" + fromId +
                ", toId=" + toId +
                ", type=" + type +
                ", coinId=" + coinId +
                ", coinName=" + coinName +
                ", amount=" + amount +
                ", status=" + status +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                "}";
    }
}
