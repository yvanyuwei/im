package com.vm.im.common.vo.user;

import lombok.Data;

import java.util.Date;

@Data
public class FindCurrentVO {

    private String userId;
    private String friendId;
    private Integer type;
    private String nickname;
    private String lastMessage;
    private Integer delFlag;
    private Date createTime;
    private String avatar;
}
