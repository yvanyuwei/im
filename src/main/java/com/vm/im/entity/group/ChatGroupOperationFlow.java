package com.vm.im.entity.group;

import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;
import java.io.Serializable;

/**
 * <p>
 * 聊天群操作流水记录表
 * </p>
 *
 * @author zhangqi
 * @since 2019-02-20
 */
@TableName("im_chat_group_operation_flow")
public class ChatGroupOperationFlow implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 群id
     */
    private String chatGroupId;

    /**
     * 操作人id
     */
    private String operatorId;

    /**
     * 操作内容
     */
    private String content;

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

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "ChatGroupOperationFlow{" +
                "id=" + id +
                ", chatGroupId=" + chatGroupId +
                ", operatorId=" + operatorId +
                ", content=" + content +
                ", createTime=" + createTime +
                "}";
    }
}
