package com.vm.im.common.enums;

/**
 * 红包类型枚举
 */
public enum RedPacketTypeEnum {
    USER(1),

    GROUP(3),

    UNION(5)

    ;

    private int value;

    RedPacketTypeEnum(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }

    public static RedPacketTypeEnum valueOf(int value) {
        if (value == USER.value) {
            return USER;
        } else if (value == GROUP.value) {
            return GROUP;
        }  else if (value == UNION.value) {
            return UNION;
        } else {
            throw new IllegalArgumentException("illegal argment [" + value + "] for GroupLeverEnum");
        }
    }
}
