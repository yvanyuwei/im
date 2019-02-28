package com.vm.im.entity.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;
import java.io.Serializable;

/**
 * <p>
 * 用户好友
 * </p>
 *
 * @author zhangqi
 * @since 2019-02-19
 */
@TableName("im_user_friend")
public class UserFriend implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @TableId(value = "user_id", type = IdType.INPUT)
    private String userId;

    /**
     * 好友ID
     */
    private String friendId;

    /**
     * 好友昵称
     */
    private String nickname;

    /**
     * 0: 不置顶, 1: 置顶
     */
    private Integer top;

    /**
     * 好友分组
     */
    private String friendGroupId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 状态 0: 正常, 1: 已删除
     */
    private Integer delFlag;

    /**
     * 描述
     */
    private String remarks;


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

    public String getFriendGroupId() {
        return friendGroupId;
    }

    public void setFriendGroupId(String friendGroupId) {
        this.friendGroupId = friendGroupId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public String toString() {
        return "UserFriend{" +
                "userId=" + userId +
                ", friendId=" + friendId +
                ", nickname=" + nickname +
                ", top=" + top +
                ", friendGroupId=" + friendGroupId +
                ", createTime=" + createTime +
                ", delFlag=" + delFlag +
                ", remarks=" + remarks +
                "}";
    }
}
