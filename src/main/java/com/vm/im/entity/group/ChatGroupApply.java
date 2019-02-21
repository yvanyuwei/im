package com.vm.im.entity.group;

import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;
import java.io.Serializable;

/**
 * <p>
 * 聊天群邀请/申请
 * </p>
 *
 * @author zhangqi
 * @since 2019-02-20
 */
@TableName("im_chat_group_apply")
public class ChatGroupApply implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 群id
     */
    private String chatGroupId;

    /**
     * 群申请类型 1: 邀请, 3: 申请
     */
    private Integer type;

    /**
     * 申请人
     */
    private String proposerId;

    /**
     * 审批人
     */
    private String approverId;

    /**
     * 邀请人
     */
    private String inviterId;

    /**
     * 被邀请人
     */
    private String inviteeId;

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

    public String getChatGroupId() {
        return chatGroupId;
    }

    public void setChatGroupId(String chatGroupId) {
        this.chatGroupId = chatGroupId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getProposerId() {
        return proposerId;
    }

    public void setProposerId(String proposerId) {
        this.proposerId = proposerId;
    }

    public String getApproverId() {
        return approverId;
    }

    public void setApproverId(String approverId) {
        this.approverId = approverId;
    }

    public String getInviterId() {
        return inviterId;
    }

    public void setInviterId(String inviterId) {
        this.inviterId = inviterId;
    }

    public String getInviteeId() {
        return inviteeId;
    }

    public void setInviteeId(String inviteeId) {
        this.inviteeId = inviteeId;
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
        return "ChatGroupApply{" +
                "id=" + id +
                ", chatGroupId=" + chatGroupId +
                ", type=" + type +
                ", proposerId=" + proposerId +
                ", approverId=" + approverId +
                ", inviterId=" + inviterId +
                ", inviteeId=" + inviteeId +
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
