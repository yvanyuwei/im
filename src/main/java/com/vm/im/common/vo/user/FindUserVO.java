package com.vm.im.common.vo.user;

import lombok.Data;

/**
 * 查找用户值对象
 *
 * @author zhangqi
 * @since 2019-02-19
 */
@Data
public class FindUserVO {

    /**
     * 用户id
     */
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
     * 个性签名
     */
    private String sign;

    /**
     * 暂不支持
     * 类型  0:群, 1:用户
     */
    private String type;

}
