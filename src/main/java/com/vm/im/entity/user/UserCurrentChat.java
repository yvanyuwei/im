package com.vm.im.entity.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 用户当前会话
 * </p>
 *
 * @author zhangqi
 * @since 2019-03-02
 */
@TableName("im_user_current_chat")
public class UserCurrentChat implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 用户ID
     */
    @TableId(value = "user_id", type = IdType.INPUT)
    private String userId;

    /**
     * 好友ID/群组ID
     */
    private String friendId;

    /**
     * 类型 1: 好友, 3:群组
     */
    private Integer type;

    /**
     * 好友昵称
     */
    private String nickname;

    /**
     * 最后消息
     */
    private String lastMessage;

    /**
     * 状态 0: 正常, 1: 已删除
     */
    private Integer delFlag;

    /**
     * 最后消息时间
     */
    private Date createTime;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFriendId() {
        return friendId;
    }

    public void setFriendId(String friendId) {
        this.friendId = friendId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "UserCurrentChat{" +
                "userId=" + userId +
                ", friendId=" + friendId +
                ", type=" + type +
                ", nickname=" + nickname +
                ", lastMessage=" + lastMessage +
                ", delFlag=" + delFlag +
                ", createTime=" + createTime +
                "}";
    }
}
