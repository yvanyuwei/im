package com.vm.im.common.enums;

/**
 * websocket接口类型枚举
 */
public enum ChatTypeEnum {

    REGISTER,

    SINGLE_SENDING,

    GROUP_SENDING,

    USER_FRIEND_LIST,

    USER_GROUP_LIST;

    public static void main(String[] args) {
        System.out.println(ChatTypeEnum.REGISTER);
    }
}
