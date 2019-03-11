package com.vm.im.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.*;

/**
 * @ClassName: KafkaManager
 * @Description: KafkaManager
 * @Author zhangqi
 * @Date 2019年02月25日10时06分
 * @Version 1.0
 */
@Component
public class KafkaManager {
    private static final Logger LOG = LoggerFactory.getLogger(KafkaManager.class);

    private Map<String, KafkaConsumer> kafkaConsumerMap = new HashMap<>();

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ConsumerFactory<String, String> consumerFactory;

    /**
     * 生产者
     * 发送消息
     *
     * @param message 发送内容
     * @param topic   指定的主题
     */
    public void sendMeessage(String message, final String topic) {
        //发送消息，topic不存在将自动创建新的topic
        ListenableFuture<SendResult<String, String>> listenableFuture = kafkaTemplate.send(topic, message);
        //添加成功发送消息的回调和失败的回调
        listenableFuture.addCallback(
                result -> LOG.info("send message to {} success", topic),
                ex -> LOG.info("send message to {} failure,error message:{}", topic, ex.getMessage()));
    }

    /**
     * 生产者
     * 发送消息到默认主题
     *
     * @param message
     */
    public void sendMeessagedefault(String message) {
        //发送消息到默认的topic
        ListenableFuture<SendResult<String, String>> listenableFuture = kafkaTemplate.sendDefault(message);
        //添加成功发送消息的回调和失败的回调
        listenableFuture.addCallback(
                result -> LOG.info("send message to default success"),
                ex -> LOG.info("send message to default failure,error message:{}", ex.getMessage()));
    }

    /**
     * 消费者
     * 订阅指定主题
     *
     * @param group    一般使用用户id
     * @param topicIds 主题id(可定义多个)
     */
    @Async
    public void consumerSubscribe(String group, List<String> topicIds) {
        LOG.info("================Kafka================用户:{}, 订阅了主题", group);
        KafkaConsumer kafkaConsumer = new KafkaConsumer(group, topicIds, consumerFactory);
        kafkaConsumerMap.put(group, kafkaConsumer);

        kafkaConsumer.subscribe();
    }

    /**
     * 消费者
     * 取消指定用户订阅
     *
     * @param group 一般使用用户id
     */
    public void consumerUnsubscribe(String group) {
        LOG.info("================Kafka================用户:{}, 取消订阅", group);
        KafkaConsumer kafkaConsumer = kafkaConsumerMap.get(group);
        kafkaConsumer.close();
        kafkaConsumerMap.remove(group);
    }
}
