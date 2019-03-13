package com.vm.im.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * ClassName:ChildChannelHandler
 * Function:
 *
 * @author hxy
 */
public class ChildChannelHandler extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel e){
        // 设置30秒没有读到数据，则触发一个READER_IDLE事件。
        e.pipeline().addLast("ping", new IdleStateHandler(60, 30, 60 * 10, TimeUnit.SECONDS));
        // pipeline.addLast(new IdleStateHandler(30, 0, 0));
        // HttpServerCodec：将请求和应答消息解码为HTTP消息
        e.pipeline().addLast("http-codec", new HttpServerCodec());
        // HttpObjectAggregator：将HTTP消息的多个部分合成一条完整的HTTP消息
        e.pipeline().addLast("aggregator", new HttpObjectAggregator(65536));
        // ChunkedWriteHandler：向客户端发送HTML5文件
        e.pipeline().addLast("http-chunked", new ChunkedWriteHandler());
        // 在管道中添加我们自己的接收数据实现方法
        e.pipeline().addLast("http-handler",new HttpRequestHandler());

        e.pipeline().addLast("websocket-handler", new MyWebSocketServerHandler());
    }
}
