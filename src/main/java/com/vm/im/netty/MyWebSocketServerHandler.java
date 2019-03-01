package com.vm.im.netty;

import com.alibaba.fastjson.JSONObject;
import com.vm.im.common.util.ResponseJson;
import com.vm.im.service.chat.ChatService;
import io.netty.channel.*;
import io.netty.handler.codec.http.websocketx.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import java.util.logging.Logger;

/**
 * ClassName:MyWebSocketServerHandler Function: TODO ADD FUNCTION.
 *
 * @author hxy
 */
@Component
@ChannelHandler.Sharable
public class MyWebSocketServerHandler extends SimpleChannelInboundHandler<WebSocketFrame> {
    private static final Logger logger = Logger.getLogger(WebSocketServerHandshaker.class.getName());
    //private WebSocketServerHandshaker handshaker;

    @Autowired
    private ChatService chatService;

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
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame msg) throws Exception {
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
            sendErrorMessage(ctx, "本例程仅支持文本消息，不支持二进制消息");
        }
        //客服端发送过来的消息
        String request = ((TextWebSocketFrame) frame).text();
        System.out.println("服务端收到：" + request);
        JSONObject param = null;
        try {
            param = JSONObject.parseObject(request);
        } catch (Exception e) {
            sendErrorMessage(ctx, "JSON字符串转换出错！");
            e.printStackTrace();
        }
        if (param == null) {
            sendErrorMessage(ctx, "消息参数为空！");
            return;
        }
        String type = (String) param.get("type");
        switch (type){
            case "REGISTER":
                chatService.register(param, ctx);
                break;
            case "SINGLE_SENDING":
                chatService.singleSend(param, ctx);
                break;
            case "GROUP_SENDING":
                chatService.groupSend(param, ctx);
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
    public void channelInactive(ChannelHandlerContext ctx) {
        chatService.remove(ctx);
    }

    /**
     * exception 异常 Caught 抓住 抓住异常，当发生异常的时候，可以做
     * 一些相应的处理，比如打印日志、关闭链接
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    private void sendErrorMessage(ChannelHandlerContext ctx, String errorMsg) {
        String responseJson = new ResponseJson()
                .error(errorMsg)
                .toString();
        ctx.channel().writeAndFlush(new TextWebSocketFrame(responseJson));
    }

}