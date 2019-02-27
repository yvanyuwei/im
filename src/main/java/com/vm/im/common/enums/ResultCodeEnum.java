package com.vm.im.common.enums;

public enum ResultCodeEnum {

    //成功
    SUCCESS(200),

    //失败
    FAIL(500)


    ;

    private Integer code;


    private ResultCodeEnum(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

}
