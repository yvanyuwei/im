package com.vm.im.entity.group;

import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;
import java.io.Serializable;

/**
 * <p>
 * 群成员表
 * </p>
 *
 * @author zhangqi
 * @since 2019-02-20
 */
@TableName("im_chat_group_user")
public class ChatGroupUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 群id
     */
    private String chatGroupId;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 用户群昵称
     */
    private String nickname;

    /**
     * 成员类型 1: 群主, 3: 管理员, 5: 普通成员
     */
    private Integer type;

    /**
     * 状态 0: 不可发言, 1: 可发言
     */
    private Integer canSpeak;

    /**
     * 入群时间
     */
    private Date createTime;


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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getCanSpeak() {
        return canSpeak;
    }

    public void setCanSpeak(Integer canSpeak) {
        this.canSpeak = canSpeak;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "ChatGroupUser{" +
                "chatGroupId=" + chatGroupId +
                ", userId=" + userId +
                ", nickname=" + nickname +
                ", type=" + type +
                ", canSpeak=" + canSpeak +
                ", createTime=" + createTime +
                "}";
    }
}
