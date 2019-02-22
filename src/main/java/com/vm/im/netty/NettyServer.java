package com.vm.im.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * ClassName:NettyServer 注解式随spring启动
 * Function: TODO ADD FUNCTION.
 *
 * @author hxy
 */
@Component
public class NettyServer {


    private static int inetPort = 7397;

    @Value("${netty.inetPort}")
    public void setPort(int inetPort) {
        NettyServer.inetPort = inetPort;
    }

    public static void main(String[] args) {
        new NettyServer().run();
    }

    @PostConstruct
    public void initNetty() {
        new Thread() {
            public void run() {
                new NettyServer().run();
            }
        }.start();
    }

    public void run() {
        System.out.println("===========================Netty端口启动==============================="+inetPort);
        // Boss线程：由这个线程池提供的线程是boss种类的，用于创建、连接、绑定socket， （有点像门卫）然后把这些socket传给worker线程池。
        // 在服务器端每个监听的socket都有一个boss线程来处理。在客户端，只有一个boss线程来处理所有的socket。
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        // Worker线程：Worker线程执行所有的异步I/O，即处理操作
        EventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            // ServerBootstrap 启动NIO服务的辅助启动类,负责初始话netty服务器，并且开始监听端口的socket请求
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workGroup);
            // 设置非阻塞,用它来建立新accept的连接,用于构造serversocketchannel的工厂类
            b.channel(NioServerSocketChannel.class);
            // ChildChannelHandler 对出入的数据进行的业务操作,其继承ChannelInitializer
            b.childHandler(new ChildChannelHandler());
            Channel ch = b.bind(inetPort).sync().channel();
            ch.closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}