package com.vm.im.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.Future;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 * ClassName:NettyServer 注解式随spring启动
 * Function: TODO ADD FUNCTION.
 *
 * @author hxy
 */
@Component
public class NettyServer implements Runnable{
    private final Logger log = LoggerFactory.getLogger(NettyServer.class);

    //private EventLoopGroup bossGroup;

    //private ChannelHandler childChannelHandler;

    private ChannelFuture serverChannelFuture;

    private static int inetPort = 7397;

    @Value("${netty.inetPort}")
    public void setPort(int inetPort) {
        NettyServer.inetPort = inetPort;
    }

    /*public static void main(String[] args) {
        new NettyServer().run();
    }*/

    public NettyServer(){

    }

    /*@PostConstruct
    public void initNetty() {
        new Thread() {
            public void run() {
                new NettyServer().run();
            }
        }.start();
    }*/

    @Override
    public void run() {
        build();
    }

    /*public void runNetty() {
        long begin = System.currentTimeMillis();
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
            long end = System.currentTimeMillis();
            System.out.println(inetPort + "+" + (end - begin));
            //log.info("Netty Websocket服务器启动完成，耗时 " + (end - begin) + " ms，已绑定端口 " + inetPort + " 阻塞式等候客户端连接");
            ch.closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }*/

    public void build() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            long begin = System.currentTimeMillis();
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup) //boss辅助客户端的tcp连接请求  worker负责与客户端之前的读写操作
                    .channel(NioServerSocketChannel.class) //配置客户端的channel类型
                    .option(ChannelOption.SO_BACKLOG, 1024) //配置TCP参数，握手字符串长度设置
                    .option(ChannelOption.TCP_NODELAY, true) //TCP_NODELAY算法，尽可能发送大块数据，减少充斥的小块数据
                    .childOption(ChannelOption.SO_KEEPALIVE, true)//开启心跳包活机制，就是客户端、服务端建立连接处于ESTABLISHED状态，超过2小时没有交流，机制会被启动
                    .childOption(ChannelOption.RCVBUF_ALLOCATOR, new FixedRecvByteBufAllocator(592048))//配置固定长度接收缓存区分配器
                    .childHandler(new ChildChannelHandler()); //绑定I/O事件的处理类,WebSocketChildChannelHandler中定义
            long end = System.currentTimeMillis();
            //log.info("Netty Websocket服务器启动完成，耗时 " + (end - begin) + " ms，已绑定端口 " + inetPort + " 阻塞式等候客户端连接");
            System.out.println("Netty Websocket服务器启动完成，耗时 " + (end - begin) + " 已绑定端口 " + inetPort + " 阻塞式等候客户端连接");
            //serverChannelFuture = serverBootstrap.bind(inetPort).sync();
            Channel ch = serverBootstrap.bind(inetPort).sync().channel();
            ch.closeFuture().sync();
        } catch (Exception e) {
            log.info(e.getMessage());
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
            e.printStackTrace();
        }

    }

    /**
     * 描述：关闭Netty Websocket服务器，主要是释放连接
     *     连接包括：服务器连接serverChannel，
     *     客户端TCP处理连接bossGroup，
     *     客户端I/O操作连接workerGroup
     *
     *     若只使用
     *         bossGroupFuture = bossGroup.shutdownGracefully();
     *         workerGroupFuture = workerGroup.shutdownGracefully();
     *     会造成内存泄漏。
     */
    public void close(){
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        serverChannelFuture.channel().close();
        Future<?> bossGroupFuture = bossGroup.shutdownGracefully();
        Future<?> workerGroupFuture = workerGroup.shutdownGracefully();

        try {
            bossGroupFuture.await();
            workerGroupFuture.await();
        } catch (InterruptedException ignore) {
            ignore.printStackTrace();
        }
    }
}