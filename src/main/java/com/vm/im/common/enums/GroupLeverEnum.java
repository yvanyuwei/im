package com.vm.im.common.enums;

/**
 * 群用等级枚举
 */
public enum GroupLeverEnum {
    ONE(1),

    THREE(3),

    FIVE(5),

    UNION(9)

    ;

    private int value;

    GroupLeverEnum(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }

    public static GroupLeverEnum valueOf(int value) {
        if (value == ONE.value) {
            return ONE;
        } else if (value == THREE.value) {
            return THREE;
        } else if (value == FIVE.value) {
            return FIVE;
        } else if (value == UNION.value) {
            return UNION;
        } else {
            throw new IllegalArgumentException("illegal argment [" + value + "] for GroupLeverEnum");
        }
    }
}
