package com.vm.im.common.enums;

/**
 * 内部管理权限枚举
 */
public enum AdminRoleEnum {

    ADMIN(1);

    private int value;

    AdminRoleEnum(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }

    public static AdminRoleEnum valueOf(int value) {
        if (value == ADMIN.value) {
            return ADMIN;
        } else {
            throw new IllegalArgumentException("illegal argment [" + value + "] for IcoRole");
        }
    }
}
