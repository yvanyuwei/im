package com.vm.im.common.exception;

public class BusinessException extends RuntimeException {
    // 失败码
    private String failCode;

    // 失败原因
    private String failReason;


    public BusinessException() {
    }

    public BusinessException(String failCode) {
        this.failCode = failCode;
    }

    public BusinessException(String failCode, String failReason) {
        this.failCode = failCode;
        this.failReason = failReason;
    }

    public BusinessException(String failCode, String failReason, Throwable cause) {
        super(cause);
        this.failCode = failCode;
        this.failReason = failReason;
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public String getFailCode() {
        return failCode;
    }

    public String getFailReason() {
        return failReason;
    }

    @Override
    public String getMessage() {
        return "[" + failCode + "] " + failReason;
    }
}
