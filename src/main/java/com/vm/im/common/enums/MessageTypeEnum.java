package com.vm.im.common.enums;

/**
 * 查找用户类型枚举
 */
public enum MessageTypeEnum {

    COMMON_MSG(1),

    RED_PACKET_MSG(3),

    SYSTEM_MSG(5);

    private int type;

    MessageTypeEnum(int type) {
        this.type = type;
    }

    public int type() {
        return type;
    }

    public static MessageTypeEnum valueOf(int type) {
        if (type == COMMON_MSG.type) {
            return COMMON_MSG;
        } else if (type == RED_PACKET_MSG.type) {
            return RED_PACKET_MSG;
        } else if (type == SYSTEM_MSG.type) {
            return SYSTEM_MSG;
        } else {
            throw new IllegalArgumentException("illegal argment [" + type + "] for FindUserTypeEnum");
        }
    }
}
