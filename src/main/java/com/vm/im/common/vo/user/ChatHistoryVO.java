package com.vm.im.common.vo.user;

import com.vm.im.common.enums.MessageTypeEnum;
import lombok.Data;

/**
 * @ClassName: ChatHistoryVO
 * @Description: 聊天历史值对象
 * @Author zhangqi
 * @Date 2019年03月26日11时24分
 * @Version 1.0
 */
@Data
public class ChatHistoryVO {

    /**
     * 发送者
     */
    private String fromUserId;

    /**
     * 接受者
     */
    private String toUserId;

    /**
     * 接受者
     */
    private String toGroupId;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 单聊或群聊  ChatTypeEnum
     */
    private String type;

    /**
     * 消息创建时间
     */
    private String createTime;

    /**
     * 消息发送者昵称
     */
    private String nickName;

    /**
     * 消息发送者头像
     */
    private String fromUserIdAvatar;

    /**
     * 消息类型 MessageTypeEnum
     */
    private int msgType = MessageTypeEnum.COMMON_MSG.type();

}
