package com.vm.im.service.chat.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.vm.im.common.constant.CommonConstant;
import com.vm.im.common.enums.ChatTypeEnum;
import com.vm.im.common.util.ResponseJson;
import com.vm.im.entity.user.UserChatGroup;
import com.vm.im.kafka.KafkaManager;
import com.vm.im.netty.BaseWebSocketServerHandler;
import com.vm.im.netty.Constant;
import com.vm.im.service.chat.ChatService;
import com.vm.im.service.common.MessageService;
import com.vm.im.service.group.ChatGroupService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
public class ChatServiceImpl extends BaseWebSocketServerHandler implements ChatService {
    private static final Logger log = LoggerFactory.getLogger(ChatServiceImpl.class);

    @Autowired
    private ChatGroupService chatGroupService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private KafkaManager kafkaManager;

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
        Long createTime = Long.valueOf(String.valueOf(param.get("createTime")));
        String fromUserIdAvatar = String.valueOf(param.get("fromUserIdAvatar"));
        ChannelHandlerContext toUserCtx = Constant.onlineUserMap.get(toUserId);
        messageService.saveMessage(param);
        String responseJson = new ResponseJson().success()
                .setData("fromUserId", fromUserId)
                .setData("toUserId",toUserId)
                .setData("content", content)
                .setData("type", ChatTypeEnum.SINGLE_SENDING)
                .setData("createTime",createTime)
                .setData("fromUserIdAvatar",fromUserIdAvatar)
                .toString();
        log.info("==============================发送的消息为：" + responseJson);
        //kafkaManager.sendMeessage(responseJson, fromUserId + CommonConstant.USER_TOPIC);
        sendMessage(toUserCtx, responseJson);
    }

    @Override
    public void groupSend(JSONObject param, ChannelHandlerContext ctx) {
        String fromUserId = (String)param.get("fromUserId");
        String toGroupId = (String)param.get("toGroupId");
        String content = (String)param.get("content");
        Long createTime = Long.valueOf(String.valueOf(param.get("createTime")));
        String fromUserIdAvatar = String.valueOf(param.get("fromUserIdAvatar"));
        messageService.saveMessage(param);
        List<UserChatGroup> groupInfo = chatGroupService.getByGroupId(toGroupId);
        if (groupInfo == null) {
            String responseJson = new ResponseJson().error("该群id不存在").toString();
            sendMessage(ctx, responseJson);
        } else {
            String responseJson = new ResponseJson().success()
                    .setData("fromUserId", fromUserId)
                    .setData("content", content)
                    .setData("toGroupId", toGroupId)
                    .setData("type", ChatTypeEnum.GROUP_SENDING)
                    .setData("createTime",createTime)
                    .setData("fromUserIdAvatar",fromUserIdAvatar)
                    .toString();
            kafkaManager.sendMeessage(responseJson,toGroupId + CommonConstant.GROUP_TOPIC);
            //给群里每个成员发信息
            /*groupInfo.stream()
                    .forEach(item -> {
                        ChannelHandlerContext toCtx = Constant.onlineUserMap.get(item.getUserId());
                        if (toCtx != null && !item.getUserId().equals(fromUserId)) {
                            sendMessage(toCtx, responseJson);
                        }else {
                            // todo 存卡夫卡消费离线数据读取到数据库
                        }
                    });*/
        }

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
                .error("该type类型不存在！")
                .toString();
        sendMessage(ctx, responseJson);
    }


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
    }
}
