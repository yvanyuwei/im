package com.vm.im.service.chat;

import com.alibaba.fastjson.JSONObject;
import com.vm.im.entity.user.User;
import io.netty.channel.ChannelHandlerContext;

public interface ChatService {

    /**
     * 用户上线注册进用户表
     * @param param
     * @param ctx
     */
    void register(JSONObject param, ChannelHandlerContext ctx);

    /**
     * 单聊
     * @param param
     * @param ctx
     */
    void singleSend(JSONObject param, ChannelHandlerContext ctx,User fromUser,User toUser);

    /**
     * 群聊
     * @param param
     * @param ctx
     */
    void groupSend(JSONObject param, ChannelHandlerContext ctx,User fromUser);

    /**
     * 移除注册用户表用户
     * @param ctx
     */
    void remove(ChannelHandlerContext ctx);

    /**
     * 错误类型
     * @param ctx
     */
    void typeError(ChannelHandlerContext ctx);
}
