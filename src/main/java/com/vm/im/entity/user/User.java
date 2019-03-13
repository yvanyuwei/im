package com.vm.im.entity.user;

import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;
import java.io.Serializable;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author zhangqi
 * @since 2019-02-19
 */
@TableName("im_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 名称
     */
    private String name;

    /**
     * 性别 0: 女, 1: 男, 3: 保密
     */
    private Integer sex;

    /**
     * 生日
     */
    private Date birthday;

    /**
     * 个性签名
     */
    private String sign;

    /**
     * 电话
     */
    private String mobile;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 密码
     */
    private String password;

    /**
     * iv
     */
    private String iv;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 状态 0: 正常, 1: 已删除
     */
    private Integer delFlag;

    /**
     * 创建时间
     */
    private Date createTime;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIv() {
        return iv;
    }

    public void setIv(String iv) {
        this.iv = iv;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", avatar=" + avatar +
                ", name=" + name +
                ", sex=" + sex +
                ", birthday=" + birthday +
                ", sign=" + sign +
                ", mobile=" + mobile +
                ", email=" + email +
                ", password=" + password +
                ", iv=" + iv +
                ", remarks=" + remarks +
                ", delFlag=" + delFlag +
                ", createTime=" + createTime +
                "}";
    }
}
