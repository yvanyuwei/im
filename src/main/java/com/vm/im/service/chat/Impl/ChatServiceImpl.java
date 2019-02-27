package com.vm.im.service.chat.Impl;

import com.alibaba.fastjson.JSONObject;
import com.vm.im.common.enums.ChatTypeEnum;
import com.vm.im.common.util.ResponseJson;
import com.vm.im.netty.BaseWebSocketServerHandler;
import com.vm.im.netty.Constant;
import com.vm.im.service.chat.ChatService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Iterator;
import java.util.Map;

@Service
public class ChatServiceImpl extends BaseWebSocketServerHandler implements ChatService {
    private static final Logger log = LoggerFactory.getLogger(ChatServiceImpl.class);

    @Override
    public void register(JSONObject param, ChannelHandlerContext ctx) {
        String userId = (String)param.get("userId");
        Constant.onlineUserMap.put(userId, ctx);
        String responseJson = new ResponseJson().success()
                .setData("type", ChatTypeEnum.REGISTER).toString();
        sendMessage(ctx, responseJson);
        //push(ctx,responseJson);
        log.info(MessageFormat.format("userId为 {0} 的用户登记到在线用户表，当前在线人数为：{1}"
                , userId, Constant.onlineUserMap.size()));
    }

    @Override
    public void singleSend(JSONObject param, ChannelHandlerContext ctx) {
        String fromUserId = (String)param.get("fromUserId");
        String toUserId = (String)param.get("toUserId");
        String content = (String)param.get("content");
        ChannelHandlerContext toUserCtx = Constant.onlineUserMap.get(toUserId);
        String responseJson = new ResponseJson().success()
                .setData("fromUserId", fromUserId)
                .setData("content", content)
                .setData("type", ChatTypeEnum.SINGLE_SENDING)
                .toString();
        log.info("==================发送的消息为：" + responseJson);
        sendMessage(toUserCtx, responseJson);
    }

    @Override
    public void groupSend(JSONObject param, ChannelHandlerContext ctx) {

    }

    @Override
    public void remove(ChannelHandlerContext ctx) {
        Iterator<Map.Entry<String, ChannelHandlerContext>> iterator =
                Constant.onlineUserMap.entrySet().iterator();
        while(iterator.hasNext()) {
            Map.Entry<String, ChannelHandlerContext> entry = iterator.next();
            if (entry.getValue() == ctx) {
                log.info("==============正在移除握手实例==========");
                Constant.webSocketHandshakerMap.remove(ctx.channel().id().asLongText());
                log.info(MessageFormat.format("已移除握手实例，当前握手实例总数为：{0}"
                        , Constant.webSocketHandshakerMap.size()));
                iterator.remove();
                log.info(MessageFormat.format("userId为 {0} 的用户已退出聊天，当前在线人数为：{1}"
                        , entry.getKey(), Constant.onlineUserMap.size()));
                break;
            }
        }
    }

    private void sendMessage(ChannelHandlerContext ctx, String message) {
        ctx.channel().writeAndFlush(new TextWebSocketFrame(message));
    }

    @Override
    public void typeError(ChannelHandlerContext ctx) {
        String responseJson = new ResponseJson()
                .error("该类型不存在！")
                .toString();
        sendMessage(ctx, responseJson);
    }


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
    }
}
