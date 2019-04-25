package com.vm.im.common.enums;

public enum BusinessExceptionEnum {

    //未知的系统异常
    UNKNOWN_SYSTEM_EXCEPTION("500", "Unknown system exception"),

    /**
     * 用户相关异常
     */
    //用户认证异常
    USER_AUTH_EXCEPTION("1000", "User authentication exception"),
    //用户不存在
    USER_NOT_EXIST_EXCEPTION("1001", "The user does not exist"),
    //用户存在
    USER_EXIST_EXCEPTION("1002", "User exist exception"),
    //redis用户信息解析异常
    USER_INFO_PARSING_EXCEPTION("1003", "User information parsing exception"),
    //访问过于频繁
    USER_SEND_TOO_FREQUENTLY("1004","User send information too frequently"),
    //用户发送异常
    USER_SEND_CONTENT_EXCEPTION("1005","User send information exception"),
    //用户信息异常
    USER_INFO_EXCEPTION("1006","User info exception"),

    /**
     * 群组相关异常
     */
    //群组不存在
    GROUP_NOT_FOUND_EXCEPTION("2000", "Group does not have exception"),
    //群组已存在
    GROUP_EXIST_EXCEPTION("2001", "Group of existing exception"),
    //群组权限认证失败
    GROUP_AUTH_EXCEPTION("2002", "Group permission authentication exception"),
    //群组成员已存在
    GROUP_MEMBER_EXIST_EXCEPTION("2003", "Group member already exists"),
    //群组成员不存在
    GROUP_MEMBER_NOT_EXIST_EXCEPTION("2004", "Group members do not exist"),
    //redis用户信息解析异常
    GROUP_INFO_PARSING_EXCEPTION("2005", "Group information parsing exception"),


    /**
     * admin相关异常
     */
    //admin认证异常
    ADMIN_AUTH_EXCEPTION("3000", "Admin authentication exception"),


    /**
     * 红包相关异常
     */
    //红包异常
    RED_PACKET_EXCEPTION("4000", "Abnormal red envelopes"),
    //红包已存在
    RED_PACKET_EXIST_EXCEPTION("4001", "RedPacket of existing exception"),
    //红包不存在
    RED_PACKET_NOT_FOUND_EXCEPTION("4002", "RedPacket does not have exception"),
    //红包状态异常
    RED_PACKET_STATUS_EXCEPTION("4003", "Abnormal red packet status"),
    //红包类型异常
    RED_PACKET_TYPE_EXCEPTION("4004", "Abnormal red packet type"),





    ;

    private String failCode;

    private String failReason;

    private BusinessExceptionEnum(String failCode, String failReason) {
        this.failCode = failCode;
        this.failReason = failReason;
    }

    public String getFailCode() {
        return failCode;
    }

    public void setFailCode(String failCode) {
        this.failCode = failCode;
    }

    public String getFailReason() {
        return failReason;
    }

    public void setFailReason(String failReason) {
        this.failReason = failReason;
    }
}
