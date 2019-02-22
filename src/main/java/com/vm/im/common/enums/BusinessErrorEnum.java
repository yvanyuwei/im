package com.vm.im.common.enums;

public enum BusinessErrorEnum {

    //未知的系统异常
    UNKNOWN_SYSTEM_EXCEPTION("500", "Unknown system exception"),

    ;

    private String failCode;

    private String failReason;

    private BusinessErrorEnum(String failCode, String failReason) {
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
