package com.vm.im.common.enums;

public enum ResultCodeEnum {

    //成功
    SUCCESS("200"),

    //失败
    FAIL("500")


    ;

    private String code;


    private ResultCodeEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
