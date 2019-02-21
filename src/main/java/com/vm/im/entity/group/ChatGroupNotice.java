package com.vm.im.entity.group;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.util.Date;
import java.io.Serializable;

/**
 * <p>
 * 群公告表
 * </p>
 *
 * @author zhangqi
 * @since 2019-02-20
 */
@TableName("im_chat_group_notice")
public class ChatGroupNotice implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 群id
     */
    private String chatGroupId;

    /**
     * 内容
     */
    private String content;

    /**
     * 创建时间
     */
    private Date createTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getChatGroupId() {
        return chatGroupId;
    }

    public void setChatGroupId(String chatGroupId) {
        this.chatGroupId = chatGroupId;
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
        return "ChatGroupNotice{" +
                "id=" + id +
                ", chatGroupId=" + chatGroupId +
                ", content=" + content +
                ", createTime=" + createTime +
                "}";
    }
}
