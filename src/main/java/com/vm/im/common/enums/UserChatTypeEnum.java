package com.vm.im.common.enums;

/**
 * 聊天类型枚举
 */
public enum UserChatTypeEnum {

    //单聊
    SINGLE(1),

    //群聊
    GROUP(3);

    private int value;

    UserChatTypeEnum(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }

    public static UserChatTypeEnum valueOf(int value) {
        if (value == SINGLE.value) {
            return SINGLE;
        } else if (value == GROUP.value) {
            return GROUP;
        } else {
            throw new IllegalArgumentException("illegal argment [" + value + "] for AdminRoleEnum");
        }
    }
}
