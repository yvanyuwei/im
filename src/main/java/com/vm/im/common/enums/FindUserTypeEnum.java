package com.vm.im.common.enums;

/**
 * 查找用户类型枚举
 */
public enum FindUserTypeEnum {

    CHAT_GROUP(1),

    CURRENT_CHAT(2),

    FRIEND(3),

    ALL(4);

    private int type;

    FindUserTypeEnum(int type) {
        this.type = type;
    }

    public int type() {
        return type;
    }

    public static FindUserTypeEnum valueOf(int type) {
        if (type == CHAT_GROUP.type) {
            return CHAT_GROUP;
        } else if (type == CURRENT_CHAT.type) {
            return CURRENT_CHAT;
        } else if (type == FRIEND.type) {
            return FRIEND;
        } else if (type == ALL.type) {
            return ALL;
        } else {
            throw new IllegalArgumentException("illegal argment [" + type + "] for FindUserTypeEnum");
        }
    }
}
