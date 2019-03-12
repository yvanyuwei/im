package com.vm.im.common.dto.user;

import lombok.Data;

import javax.validation.constraints.NotEmpty;


@Data
public class UserCurrentDTO {
    @NotEmpty(message = "uid不能为空")
    private String uid;

    @NotEmpty(message = "friendId不能为空")
    private String friendId;

    @NotEmpty(message = "message不能为空")
    private String lastMessage;

    @NotEmpty(message = "message不能为空")
    private Integer type;

    private String nickName;


}
