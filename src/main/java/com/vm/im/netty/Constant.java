package com.vm.im.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 常量
 * */
public class Constant {

    public static final String USER_TOKEN = "userId";
    
    //存放某一类的channel
    public static  Map<String, ChannelGroup> groupMap = new ConcurrentHashMap<String, ChannelGroup>();

    //webSocketServerHandshaker表，用channelId为键，存放握手实例。用来响应CloseWebSocketFrame的请求
    public static Map<String, WebSocketServerHandshaker> webSocketHandshakerMap =
            new ConcurrentHashMap<String, WebSocketServerHandshaker>();

    //onlineUser表，用userId为键，存放在线的客户端连接上下文
    public static Map<String, ChannelHandlerContext> onlineUserMap =
            new ConcurrentHashMap<String, ChannelHandlerContext>();

    public static ChannelGroup aaChannelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
}