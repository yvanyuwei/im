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

    USER_EXIST_EXCEPTION("1001", "User exist exception"),


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


    /**
     * admin相关异常
     */
    //admin认证异常
    ADMIN_AUTH_EXCEPTION("3000", "Admin authentication exception")


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
