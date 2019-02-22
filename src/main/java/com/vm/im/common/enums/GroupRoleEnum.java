package com.vm.im.common.enums;

/**
 * 群用户权限枚举
 */
public enum GroupRoleEnum {
    MASTER(1),

    ADMIN(3),

    USER(5);

    private int value;

    GroupRoleEnum(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }

    public static GroupRoleEnum valueOf(int value) {
        if (value == ADMIN.value) {
            return ADMIN;
        } else if (value == MASTER.value) {
            return MASTER;
        } else if (value == USER.value) {
            return USER;
        } else {
            throw new IllegalArgumentException("illegal argment [" + value + "] for IcoRole");
        }
    }
}
