package com.vm.im.common.vo.user;

import lombok.Data;

@Data
public class UserToken {
    private long id;
    private String username;
    private String image;
    private long createtime;
    private String mail;
    private String phonenum;
    private String token;
    private String googlesecret;
    private int hasemailauth;
    private int hasfreeze;
    private int hasgoogleauth;
    private int hasphoneauth;
    private int hasrealnameauth;
    private String password;
    private int regisphormail;
    private String tradepassword;
    private int updatephonemail;
    private int userlevel;
}
