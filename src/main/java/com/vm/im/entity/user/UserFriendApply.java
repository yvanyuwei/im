package com.vm.im.entity.user;

import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;
import java.io.Serializable;

/**
 * <p>
 * 用户好友申请
 * </p>
 *
 * @author zhangqi
 * @since 2019-02-19
 */
@TableName("im_user_friend_apply")
public class UserFriendApply implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 好友ID
     */
    private String friendId;

    /**
     * 申请来源 1: 账号查找, 3: 群, 5: 好友推荐
     */
    private Integer applySource;

    /**
     * 申请源id 群Id/好友Id
     */
    private String applySourceId;

    /**
     * 说明
     */
    private String remarks;

    /**
     * 状态 0: 拒绝, 1: 同意
     */
    private Integer status;

    /**
     * 拒绝原因
     */
    private String refusalCause;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 更新者
     */
    private String updateBy;


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

    public String getFriendId() {
        return friendId;
    }

    public void setFriendId(String friendId) {
        this.friendId = friendId;
    }

    public Integer getApplySource() {
        return applySource;
    }

    public void setApplySource(Integer applySource) {
        this.applySource = applySource;
    }

    public String getApplySourceId() {
        return applySourceId;
    }

    public void setApplySourceId(String applySourceId) {
        this.applySourceId = applySourceId;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRefusalCause() {
        return refusalCause;
    }

    public void setRefusalCause(String refusalCause) {
        this.refusalCause = refusalCause;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    @Override
    public String toString() {
        return "UserFriendApply{" +
                "id=" + id +
                ", userId=" + userId +
                ", friendId=" + friendId +
                ", applySource=" + applySource +
                ", applySourceId=" + applySourceId +
                ", remarks=" + remarks +
                ", status=" + status +
                ", refusalCause=" + refusalCause +
                ", createTime=" + createTime +
                ", createBy=" + createBy +
                ", updateTime=" + updateTime +
                ", updateBy=" + updateBy +
                "}";
    }
}
