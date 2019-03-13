package com.vm.im.common.exception;

public class NotSupportedException extends RuntimeException {
    // 失败码
    private String failCode;

    // 失败原因
    private String failReason;


    public NotSupportedException() {
    }

    public NotSupportedException(String failCode) {
        this.failCode = failCode;
    }

    public NotSupportedException(String failCode, String failReason) {
        this.failCode = failCode;
        this.failReason = failReason;
    }

    public NotSupportedException(String failCode, String failReason, Throwable cause) {
        super(cause);
        this.failCode = failCode;
        this.failReason = failReason;
    }

    public NotSupportedException(Throwable cause) {
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
