package com.vm.im.netty;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.vm.im.common.dto.ResultBean;
import com.vm.im.common.enums.ChatTypeEnum;
import com.vm.im.common.exception.BusinessException;
import com.vm.im.common.util.ResponseJson;
import com.vm.im.entity.user.User;
import com.vm.im.service.chat.ChatService;
import com.vm.im.service.user.UserChatGroupService;
import com.vm.im.service.user.UserCurrentChatService;
import com.vm.im.service.user.UserFriendService;
import com.vm.im.service.user.UserService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * ClassName:MyWebSocketServerHandler Function:
 *
 * @author hxy
 */
@Controller
@ChannelHandler.Sharable
public class MyWebSocketServerHandler extends SimpleChannelInboundHandler<WebSocketFrame> {
    private static final Logger LOG = LoggerFactory.getLogger(MyWebSocketServerHandler.class);

    private static ChatService chatService;

    @Autowired
    public void setChatService(ChatService chatService,UserFriendService userFriendService,
                               UserChatGroupService userChatGroupService,UserService userService,
                               UserCurrentChatService userCurrentChatService) {
        MyWebSocketServerHandler.chatService = chatService;
        MyWebSocketServerHandler.userChatGroupService = userChatGroupService;
        MyWebSocketServerHandler.userFriendService = userFriendService;
        MyWebSocketServerHandler.userService = userService;
        MyWebSocketServerHandler.userCurrentChatService = userCurrentChatService;
    }

    private static UserFriendService userFriendService;

    private static UserChatGroupService userChatGroupService;

    private static UserService userService;

    private static UserCurrentChatService userCurrentChatService;

    /**
     * channel 通道 action 活跃的 当客户端主动链接服务端的链接后，
     * 这个通道就是活跃的了。也就是客户端与服务端建立了通信通道并且
     * 可以传输数据
     */
    /*@Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 添加
        Global.group.add(ctx.channel());
        System.out.println("客户端与服务端连接开启：" + ctx.channel().remoteAddress().toString());
    }*/

    /**
     * 接收客户端发送的消息 channel 通道 Read 读 简而言之就是从通道中
     * 读取数据，也就是服务端接收客户端发来的数据。但是这个数据在不进行
     * 解码时它是ByteBuf类型的
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame msg) {
        handlerWebSocketFrame(ctx,msg);
    }

    /**
     *              处理WebSocketFrame
     * @param ctx
     * @param frame
     */
    private void handlerWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) {
        // 判断是否关闭链路的指令
        if (frame instanceof CloseWebSocketFrame) {
            WebSocketServerHandshaker handshaker = Constant.webSocketHandshakerMap.get(ctx.channel().id().asLongText());
            if (handshaker == null){
                sendErrorMessage(ctx,"不存在此客户端连接");
            }else {
//                chatService.remove(ctx);
                handshaker.close(ctx.channel(), (CloseWebSocketFrame) frame.retain());
            }
            return;
        }
        // 判断是否ping消息
        if (frame instanceof PingWebSocketFrame) {
            ctx.channel().write(new PongWebSocketFrame(frame.content().retain()));
            return;
        }
        // 本例程仅支持文本消息，不支持二进制消息
        if (!(frame instanceof TextWebSocketFrame)) {
            sendErrorMessage(ctx, "仅支持文本消息，不支持二进制消息");
        }
        //客服端发送过来的消息
        String request = ((TextWebSocketFrame) frame).text();
        System.out.println("服务端收到：" + request);
        LOG.info("socket 服务端收到：{}", request);
        JSONObject param = null;
        try {
            param = JSONObject.parseObject(request);
        } catch (Exception e) {
            LOG.info("JSON字符串转换出错！");
            sendErrorMessage(ctx, "参数错误！");
            return;
        }
        if (param == null) {
            sendErrorMessage(ctx, "消息参数为空！");
            return;
        }
        String type = (String) param.get("type");
        User user = null;
        if (type.equals(ChatTypeEnum.SINGLE_SENDING.name()) || type.equals(ChatTypeEnum.GROUP_SENDING.name())){
            //user = userService.getRedisUserById(String.valueOf(param.get("fromUserId")));
            try {
                user =userService.getRedisUserById(String.valueOf(param.get("fromUserId")));
            }catch (BusinessException busExp){
                String str = JSON.toJSONString(new ResultBean(Integer.parseInt(busExp.getFailCode()),
                        busExp.getFailReason(),"用户数据信息异常"));
                sendMessage(ctx,str);
                return;
            }catch (Exception e){
                LOG.error("========获取用户信息出现异常==========="+ type + e.getMessage());
            }
        }else{
            try {
                user =userService.getRedisUserById(String.valueOf(param.get("userId")));
            }catch (BusinessException busExp){
                String str = JSON.toJSONString(new ResultBean(Integer.parseInt(busExp.getFailCode()),
                        busExp.getFailReason(),"用户数据信息异常"));
                sendMessage(ctx,str);
                return;
            }catch (Exception e){
                LOG.error("========获取用户信息出现异常===========：" + type + e.getMessage());
            }
        }
        switch (type) {
            case "REGISTER":
                ChannelHandlerContext userIdCtx = Constant.onlineUserMap.get(String.valueOf(param.get("userId")));
                if(userIdCtx == null){
                    chatService.register(param,ctx);
                }else {
                    chatService.remove(userIdCtx);
                    chatService.register(param, ctx);
                }
                break;
            case "SINGLE_SENDING":
                chatService.singleSend(param, ctx,user);
                break;
            case "GROUP_SENDING":
                chatService.groupSend(param, ctx,user);
                break;
            case "USER_FRIEND_LIST":
                userFriendService.selectUserFriend(param, ctx);
                break;
            case "USER_GROUP_LIST":
                userChatGroupService.userGroupList(param, ctx);
                break;
            case "LOAD_GROUP_USER":
                userChatGroupService.loadGroupUser(param, ctx);
                break;
            case "USER_CURRENT_CHAT":
                userCurrentChatService.listByUid(param, ctx);
                break;
            default:
                chatService.typeError(ctx);
                break;
        }
        /*if (logger.isLoggable(Level.FINE)) {
            logger.fine(String.format("%s received %s", ctx.channel(), request));
        }
        TextWebSocketFrame tws = new TextWebSocketFrame(new Date().toString() + ctx.channel().id() + "：" + request);
        // 群发
        Global.group.writeAndFlush(tws);
        // 返回【谁发的发给谁】
        // ctx.channel().writeAndFlush(tws);*/
    }

    /**
     * channel 通道 Inactive 不活跃的 当客户端主动断开服务端的链接后，
     * 这个通道就是不活跃的。也就是说客户端与服务端关闭了通信通道并且不
     * 可以传输数据
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception{
        try {
            chatService.remove(ctx);
        }catch (Exception e){
            LOG.info("移除握手错误："+e.getMessage());
        }

    }

    /**
     * exception 异常 Caught 抓住 抓住异常，当发生异常的时候，可以做
     * 一些相应的处理，比如打印日志、关闭链接
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
//        try {
//            chatService.remove(ctx);
//        }catch (Exception e){
//            LOG.info("移除握手错误："+e.getMessage());
//        }
        cause.printStackTrace();
        ctx.close();
    }

    private void sendErrorMessage(ChannelHandlerContext ctx, String errorMsg) {
        String responseJson = new ResponseJson()
                .error(errorMsg)
                .toString();
        ctx.channel().writeAndFlush(new TextWebSocketFrame(responseJson));
    }

    private void sendMessage(ChannelHandlerContext ctx, String message) {
        ctx.channel().writeAndFlush(new TextWebSocketFrame(message));
    }

}