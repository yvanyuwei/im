package com.vm.im.common.vo.user;

import lombok.Data;

@Data
public class UserChatGroupVO {
    private String avatar;
    private String nickname;
    private String userId;
    private Integer type;
    private Integer canSpeak;
    private Integer top;
}
