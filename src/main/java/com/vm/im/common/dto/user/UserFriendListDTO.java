package com.vm.im.common.dto.user;

import lombok.Data;

import javax.validation.constraints.NotEmpty;


@Data
public class UserFriendListDTO {
    @NotEmpty(message = "uid不能为空")
    private String uid;

    @NotEmpty(message = "friendId不能为空")
    private String friendId;

    @NotEmpty(message = "timestamp不能为空")
    private String timestamp;

}
