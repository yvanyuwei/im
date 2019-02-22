package com.vm.im.common.enums;

/**
 * 群用户权限枚举
 */
public enum GroupRole {
    MASTER(1),

    ADMIN(2),

    USER(3);

    private int value;

    GroupRole(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }

    public static GroupRole valueOf(int value) {
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
