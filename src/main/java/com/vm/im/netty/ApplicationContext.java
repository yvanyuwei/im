package com.vm.im.netty;

import com.vm.im.service.group.ChatGroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
@Scope("singleton")
public class ApplicationContext {

    private final Logger logger = LoggerFactory.getLogger(ApplicationContext.class);

    @Autowired
    private NettyServer nettyServer;
    //@Autowired
    //private UserInfoDao userInfoDao;
    @Autowired
    private ChatGroupService chatGroupService;
    
    private Thread nettyThread;
    
    /**
     *      启动加载相应数据
     *      1. 启动Netty WebSocket服务器；
     *      2. 加载用户数据；
     *      3. 加载用户交流群数据。
     */
    @PostConstruct
    public void init() {
        nettyThread = new Thread(nettyServer);
        logger.info("开启独立线程，启动Netty WebSocket服务器...");
        nettyThread.start();
        logger.info("加载用户数据...");
        //userInfoDao.loadUserInfo();
        logger.info("加载用户交流群数据...");
        chatGroupService.loadGroupInfo();
    }

    /**
     *      服务器关闭前需要手动关闭Netty Websocket相关资源，否则会造成内存泄漏。
     *      1. 释放Netty Websocket相关连接；
     *      2. 关闭Netty Websocket服务器线程。（强行关闭，是否有必要？）
     */
    @SuppressWarnings("deprecation")
    @PreDestroy
    public void close() {
        logger.info("正在释放Netty Websocket相关连接...");
        //nettyServer.close();
        logger.info("正在关闭Netty Websocket服务器线程...");
        nettyThread.stop();
        logger.info("系统成功关闭！");
    }
}
