package com.vm.im.entity.common;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 用户群组等级
 * </p>
 *
 * @author zhangqi
 * @since 2019-02-20
 */
@TableName("im_level")
public class Level implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 等级
     */
    private Integer level;

    /**
     * 等级名称
     */
    private String levelName;

    /**
     * 人数
     */
    private Integer peopleNumber;

    private Date createTime;

    private Date updateTime;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public Integer getPeopleNumber() {
        return peopleNumber;
    }

    public void setPeopleNumber(Integer peopleNumber) {
        this.peopleNumber = peopleNumber;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "Level{" +
                "id=" + id +
                ", level=" + level +
                ", levelName=" + levelName +
                ", peopleNumber=" + peopleNumber +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                "}";
    }
}
