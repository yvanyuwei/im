package com.vm.im.common.vo.user;

import lombok.Data;

import java.util.List;

@Data
public class UserChatVO {
    //id, name, avatar, master, nickname, user_id, can_speak
    /*select
    icg.`id` groupId, icg.name, icg.avatar, icg.master, iut.*
    from (SELECT*/

    private String groupId;
    private String name;
    private String avatar;
    private String master;
    private String nickname;
    private String userId;
    private Integer canSpeak;
    private List<UserChatGroupVO> userChatGroupVO;

}
