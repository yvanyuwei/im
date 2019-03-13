package com.vm.im.entity.user;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 用户操作流水记录表
 * </p>
 *
 * @author zhangqi
 * @since 2019-02-19
 */
@TableName("im_blacklist")
public class Blacklist implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 黑用户id
     */
    private String blackUserId;

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBlackUserId() {
        return blackUserId;
    }

    public void setBlackUserId(String blackUserId) {
        this.blackUserId = blackUserId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Blacklist{" +
                "id=" + id +
                ", userId=" + userId +
                ", blackUserId=" + blackUserId +
                ", createTime=" + createTime +
                "}";
    }
}
