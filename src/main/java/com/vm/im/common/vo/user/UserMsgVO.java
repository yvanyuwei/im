package com.vm.im.common.vo.user;

import lombok.Data;

@Data
public class UserMsgVO {
    //iu.id, iu.avatar,iu.sex,iu.mobile,iu.email,iu.sign, uf.nickname, uf.friend_id
    private String id;
    private String avatar;
    private String sign;
    private Integer sex;
    private String mobile;
    private String email;
    private String nickname;
    private String friendId;
}
