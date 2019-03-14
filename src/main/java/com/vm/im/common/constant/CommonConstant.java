package com.vm.im.common.constant;

import io.swagger.models.auth.In;

/**
 * @ClassName: CommonConstant
 * @Description: constant
 * @Author zhangqi
 * @Date 2018年10月11日16时05分
 * @Version 1.0
 */
public class CommonConstant {

    /**
     *  是/正常/支持
     */
    public final static Integer YES = 1;

    /**
     *  否/冻结/不支持
     */
    public final static Integer NO = 0;

    /**
     *  请求头认证字段
     */
    public final static String AUTHORIZATION = "Authorization";

    /**
     *  请求头uid
     */
    public final static String USERID = "UserId";

    /**
     *  redis token
     */
    public final static String REDIS_TOKEN = "token";

    /**
     *  redis token 前缀
     */
    public final static String REDIS_TOKEN_PREFIX = "VM_LOGIN_TOKEN";

    /**
     *  聊天群组id
     */
    public final static String CHAT_GROUP_ID = "chatGroupId";

    /**
     *  群主题
     */
    public final static String GROUP_TOPIC = "GroupTopic";

    /**
     *  用户主题
     */
    public final static String USER_TOPIC = "Topic";


    /**
     *  模糊查询占位符 placeholder
     */
    public final static String PLACEHOLDER = "%";

    /**
     *  redis 用户信息
     */
    public final static String REDIS_USER_INFO = "UserInfo";

    /**
     * redis 工会信息
     */
    public final static String REDIS_GROUP_INFO = "GroupInfo";

    /**
     * redis key 过期时间
     */
    public final static Integer REDIS_EXPIRE_TIME = 5;

    /**
     * 设置用户发送条数
     */
    public final static Integer USER_SEND_NUMBER = 3;

}
