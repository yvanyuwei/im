package com.vm.im.common.enums;

/**
 * 聊天群组流水状态枚举
 */
public enum ChatGroupFlowTypeEnum {

    /**
     * 退出
     */
    QUIT(1),

    /**
     * 踢出
     */
    KICK(3),

    /**
     * 申请加入
     */
    APPLY(5),

    /**
     * 邀请加入
     */
    INVITE(7),

    ;

    private int value;

    ChatGroupFlowTypeEnum(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }

    public static ChatGroupFlowTypeEnum valueOf(int value) {
        if (value == QUIT.value) {
            return QUIT;
        } else if (value == KICK.value) {
            return KICK;
        } else if (value == APPLY.value) {
            return APPLY;
        } else if (value == INVITE.value) {
            return INVITE;
        } else {
            throw new IllegalArgumentException("illegal argment [" + value + "] for ChatGroupFlowTypeEnum");
        }
    }
}
