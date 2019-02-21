package com.vm.im.entity.group;

import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;
import java.io.Serializable;

/**
 * <p>
 * 聊天群人员流动记录表
 * </p>
 *
 * @author zhangqi
 * @since 2019-02-20
 */
@TableName("im_chat_group_flow")
public class ChatGroupFlow implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 群id
     */
    private String chatGroupId;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 人员流动类型 1: 退出, 3:踢出, 5: 加入, 7:被邀加入
     */
    private Integer type;

    /**
     * 踢人/邀请人
     */
    private String operatorId;

    /**
     * 创建时间
     */
    private Date createTime;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChatGroupId() {
        return chatGroupId;
    }

    public void setChatGroupId(String chatGroupId) {
        this.chatGroupId = chatGroupId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "ChatGroupFlow{" +
                "id=" + id +
                ", chatGroupId=" + chatGroupId +
                ", userId=" + userId +
                ", type=" + type +
                ", operatorId=" + operatorId +
                ", createTime=" + createTime +
                "}";
    }
}
