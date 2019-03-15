package com.vm.im.service.chat.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.vm.im.common.constant.CommonConstant;
import com.vm.im.common.dto.ResultBean;
import com.vm.im.common.enums.BusinessExceptionEnum;
import com.vm.im.common.enums.ChatTypeEnum;
import com.vm.im.common.exception.BusinessException;
import com.vm.im.common.util.RedisUtil;
import com.vm.im.common.util.ResponseJson;
import com.vm.im.common.vo.user.UserToken;
import com.vm.im.controller.aop.NeedUserAuth;
import com.vm.im.entity.user.User;
import com.vm.im.entity.user.UserChatGroup;
import com.vm.im.kafka.KafkaManager;
import com.vm.im.netty.BaseWebSocketServerHandler;
import com.vm.im.netty.Constant;
import com.vm.im.service.chat.ChatService;
import com.vm.im.service.common.MessageService;
import com.vm.im.service.group.ChatGroupService;
import com.vm.im.service.user.UserChatGroupService;
import com.vm.im.service.user.UserCurrentChatService;
import com.vm.im.service.user.UserService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.*;

@Service
public class ChatServiceImpl extends BaseWebSocketServerHandler implements ChatService {
    private static final Logger log = LoggerFactory.getLogger(ChatServiceImpl.class);

    @Autowired
    private ChatGroupService chatGroupService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserCurrentChatService userCurrentChatService;

    @Autowired
    private KafkaManager kafkaManager;

    @Autowired
    private NeedUserAuth needUserAuth;

    @Autowired
    private UserService userService;

    @Autowired
    private UserChatGroupService userChatGroupService;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public void register(JSONObject param, ChannelHandlerContext ctx) {
        String userMsg = null;
        try {
            userMsg = needUserAuth.checkToken(String.valueOf(param.get("userId")), String.valueOf(param.get("token")));
        } catch (BusinessException busExp) {
            String str = JSON.toJSONString(new ResultBean(Integer.parseInt(busExp.getFailCode()),
                    busExp.getFailReason(), "用户token验证失败"));
            sendMessage(ctx, str);
            ctx.close();
            return;
        }
        UserToken userToken = JSON.parseObject(userMsg, UserToken.class);
        User user = buildUserMessage(userToken);
        //String userMsg = needUserAuth.checkToken(String.valueOf(param.get("userId")),String.valueOf(param.get
        // ("token")));
        userService.saveUserInfo(user);
        String userId = (String) param.get("userId");
        Constant.onlineUserMap.put(userId, ctx);
        List<String> groupList = userChatGroupService.selectGroupIdByUid(userId);
        List<String> list = new ArrayList<>();
        list.add(userId + CommonConstant.USER_TOPIC);
        for (String str : groupList) {
            list.add(str + CommonConstant.GROUP_TOPIC);
        }
        kafkaManager.consumerSubscribe(userId, list);
        String responseJson = new ResponseJson().success()
                .setData("type", ChatTypeEnum.REGISTER).toString();
        sendMessage(ctx, responseJson);
        //push(ctx,responseJson);
        log.info(MessageFormat.format("userId为 {0} 的用户登记到在线用户表，当前在线人数为：{1}"
                , userId, Constant.onlineUserMap.size()));
    }

    @Override
    public void singleSend(JSONObject param, ChannelHandlerContext ctx, User fromUser, User toUser) {
        long begin = System.currentTimeMillis();
        if (redisUtil.hasKey(fromUser.getId())) {
            redisUtil.incr(fromUser.getId(), 1);
        } else {
            redisUtil.set(fromUser.getId(), String.valueOf(CommonConstant.YES), CommonConstant.REDIS_EXPIRE_TIME);
        }
        System.out.println(redisUtil.get(fromUser.getId()));
        if (Integer.parseInt(String.valueOf(redisUtil.get(fromUser.getId()))) > CommonConstant.USER_SEND_NUMBER) {
            String str = JSON.toJSONString(new ResultBean(Integer.parseInt(BusinessExceptionEnum.USER_SEND_TOO_FREQUENTLY.getFailCode()),
                    BusinessExceptionEnum.USER_SEND_TOO_FREQUENTLY.getFailReason(), "信息发送过于频繁"));
            sendMessage(ctx, str);
            return;
        }
        String fromUserId = (String) param.get("fromUserId");
        String toUserId = (String) param.get("toUserId");
        String content = String.valueOf(param.get("content"));
        if (content.length() > 1000) {
            content = content.substring(0, 1000);
        }
        Long createTime = System.currentTimeMillis();
        String fromUserIdAvatar = String.valueOf(param.get("fromUserIdAvatar"));
        //ChannelHandlerContext toUserCtx = Constant.onlineUserMap.get(toUserId);

        messageService.saveMessage(param, createTime);
        userCurrentChatService.flushCurrentMsgListForUser(fromUser, toUser, 500, param);
        String responseJson = new ResponseJson().success()
                .setData("fromUserId", fromUserId)
                .setData("toUserId", toUserId)
                .setData("content", content)
                .setData("type", ChatTypeEnum.SINGLE_SENDING)
                .setData("createTime", createTime)
                .setData("nickName", fromUser.getName())
                .setData("fromUserIdAvatar", fromUserIdAvatar)
                .toString();
        log.info("==============================发送的消息为：" + responseJson);
        kafkaManager.sendMeessage(responseJson, toUserId + CommonConstant.USER_TOPIC);
        long end = System.currentTimeMillis();
        System.out.println(end - begin);
        log.info("发送单聊耗时:{}", end - begin);
    }

    @Override
    public void groupSend(JSONObject param, ChannelHandlerContext ctx, User fromUser) {
        long begin = System.currentTimeMillis();
        if (redisUtil.hasKey(fromUser.getId())) {
            redisUtil.incr(fromUser.getId(), 1);
        } else {
            redisUtil.set(fromUser.getId(), String.valueOf(CommonConstant.YES), CommonConstant.REDIS_EXPIRE_TIME);
        }
        System.out.println(redisUtil.get(fromUser.getId()));
        if (Integer.parseInt(String.valueOf(redisUtil.get(fromUser.getId()))) > CommonConstant.USER_SEND_NUMBER) {
            String str = JSON.toJSONString(new ResultBean(Integer.parseInt(BusinessExceptionEnum.USER_SEND_TOO_FREQUENTLY.getFailCode()),
                    BusinessExceptionEnum.USER_SEND_TOO_FREQUENTLY.getFailReason(), "信息发送过于频繁"));
            sendMessage(ctx, str);
            return;
        }
        String fromUserId = (String) param.get("fromUserId");
        String toGroupId = (String) param.get("toGroupId");
        String content = String.valueOf(param.get("content"));
        if (content.length() > 1000) {
            content = content.substring(0, 1000);
        }
        Long createTime = System.currentTimeMillis();
        String fromUserIdAvatar = String.valueOf(param.get("fromUserIdAvatar"));
        messageService.saveMessage(param, createTime);
        userCurrentChatService.flushCurrentMsgListForUser(fromUser, null, 500, param);
        List<UserChatGroup> groupInfo = chatGroupService.getByGroupId(toGroupId);
        if (null == groupInfo) {
            String responseJson = new ResponseJson().error("该群id不存在").toString();
            sendMessage(ctx, responseJson);
        } else {
            String responseJson = new ResponseJson().success()
                    .setData("fromUserId", fromUserId)
                    .setData("content", content)
                    .setData("toGroupId", toGroupId)
                    .setData("type", ChatTypeEnum.GROUP_SENDING)
                    .setData("createTime", createTime)
                    .setData("nickName", fromUser.getName())
                    .setData("fromUserIdAvatar", fromUserIdAvatar)
                    .toString();
            kafkaManager.sendMeessage(responseJson, toGroupId + CommonConstant.GROUP_TOPIC);
        }
        long end = System.currentTimeMillis();
        System.out.println(end - begin);
        log.info("发送群聊耗时:{}", end - begin);
    }

    @Override
    public void remove(ChannelHandlerContext ctx) {
        Iterator<Map.Entry<String, ChannelHandlerContext>> iterator =
                Constant.onlineUserMap.entrySet().iterator();
        Constant.webSocketHandshakerMap.remove(ctx.channel().id().asLongText());
        log.info("已移除握手实例，当前握手实例总数为：{}", Constant.webSocketHandshakerMap.size());
        while (iterator.hasNext()) {
            Map.Entry<String, ChannelHandlerContext> entry = iterator.next();
            if (entry.getValue() == ctx) {
                kafkaManager.consumerUnsubscribe(entry.getKey());
                log.info("==============正在移除握手实例==========");
                iterator.remove();
                log.info("userId为 {}, 的用户已退出聊天，当前在线人数为：{}", entry.getKey(), Constant.onlineUserMap.size());
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

    /**
     * 构建用户信息
     *
     * @param userToken
     * @return
     */
    private User buildUserMessage(UserToken userToken) {
        User user = new User();
        user.setId(String.valueOf(userToken.getId()));
        user.setAvatar(userToken.getImage());
        user.setName(userToken.getUsername());
        user.setMobile(userToken.getPhonenum());
        user.setEmail(userToken.getMail());
        user.setPassword(userToken.getPassword());
        user.setDelFlag(CommonConstant.NO);
        user.setCreateTime(new Date(userToken.getCreatetime()));
        log.info("构建用户信息, userChatGroup:{}", JSON.toJSONString(user));
        return user;
    }

}
