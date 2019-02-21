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
 * @since 2019-02-19
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
     * 0: 不置顶, 1: 置顶
     */
    private Integer top;

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

    public Integer getTop() {
        return top;
    }

    public void setTop(Integer top) {
        this.top = top;
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
                ", top=" + top +
                ", delFlag=" + delFlag +
                ", createTime=" + createTime +
                "}";
    }
}
