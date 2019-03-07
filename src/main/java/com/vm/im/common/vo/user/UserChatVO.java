package com.vm.im.common.vo.user;

import lombok.Data;

import java.util.List;

@Data
public class UserChatVO {
    private String id;
    private String name;
    private String avatar;
    private String master;
    private String nickname;
    private String userId;
    private Integer canSpeak;
    private String useravatar;
    private String ChatGroupId;
    private Integer type;
    private Integer top;

    //private List<UserChatGroupVO> userChatGroupVO;

}
