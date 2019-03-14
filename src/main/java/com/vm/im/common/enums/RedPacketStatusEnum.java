package com.vm.im.common.enums;

/**
 * 红包状态枚举
 */
public enum RedPacketStatusEnum {
    FAILED(0),

    SUCCESS(1),

    COMOKETE(9)

    ;

    private int value;

    RedPacketStatusEnum(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }

    public static RedPacketStatusEnum valueOf(int value) {
        if (value == FAILED.value) {
            return FAILED;
        } else if (value == SUCCESS.value) {
            return SUCCESS;
        }  else if (value == COMOKETE.value) {
            return COMOKETE;
        } else {
            throw new IllegalArgumentException("illegal argment [" + value + "] for GroupLeverEnum");
        }
    }
}
