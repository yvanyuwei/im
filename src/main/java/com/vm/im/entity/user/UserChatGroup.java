package com.vm.im.entity.user;

import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;
import java.io.Serializable;

/**
 * <p>
 * 用户群
 * </p>
 *
 * @author zhangqi
 * @since 2019-02-28
 */
@TableName("im_user_chat_group")
public class UserChatGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 群id
     */
    private String chatGroupId;

    /**
     * 用户群昵称
     */
    private String nickname;

    /**
     * 0: 不置顶, 1: 置顶
     */
    private Integer top;

    /**
     * 成员类型 1: 群主, 3: 管理员, 5: 普通成员
     */
    private Integer type;

    /**
     * 状态 0: 不可发言, 1: 可发言
     */
    private Integer canSpeak;

    /**
     * 状态 0: 正常, 1: 已删除
     */
    private Integer delFlag;

    /**
     * 创建时间
     */
    private Date createTime;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getChatGroupId() {
        return chatGroupId;
    }

    public void setChatGroupId(String chatGroupId) {
        this.chatGroupId = chatGroupId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getTop() {
        return top;
    }

    public void setTop(Integer top) {
        this.top = top;
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
        return "UserChatGroup{" +
                "userId=" + userId +
                ", chatGroupId=" + chatGroupId +
                ", nickname=" + nickname +
                ", top=" + top +
                ", type=" + type +
                ", canSpeak=" + canSpeak +
                ", delFlag=" + delFlag +
                ", createTime=" + createTime +
                "}";
    }
}
