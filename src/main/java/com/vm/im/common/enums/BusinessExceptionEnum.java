package com.vm.im.common.enums;

public enum BusinessExceptionEnum {

    //未知的系统异常
    UNKNOWN_SYSTEM_EXCEPTION("500", "Unknown system exception"),

    //用户认证异常
    USER_AUTH_EXCEPTION("1000", "User authentication exception")

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
