package com.vm.im.common.vo.user;

import lombok.Data;

@Data
public class UserChatGroupVO {
    //iu.`avatar` useravatar,iu.`id` userid ,cg.chat_group_id,cg.nickname,cg.can_speak,cg.type,cg.top*/
    private String useravatar;
    private String userid;
    private String chat_group_id;
    private String nickname;
    private Integer canSpeak;
    private Integer type;
    private Integer top;
}
