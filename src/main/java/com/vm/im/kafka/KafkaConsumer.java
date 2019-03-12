package com.vm.im.kafka;

import com.vm.im.common.constant.CommonConstant;
import com.vm.im.netty.Constant;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.ConsumerFactory;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: KafkaConsumer
 * @Description:
 * @Author zhangqi
 * @Date 2019年02月26日14时40分
 * @Version 1.0
 */
public class KafkaConsumer {
    private static final Logger LOG = LoggerFactory.getLogger(KafkaConsumer.class);

    private String groupId;

    private List<String> topicIds;

    private boolean active = true;

    private Consumer<String, String> consumer;

    public KafkaConsumer(String groupId, List<String> topicIds, ConsumerFactory consumerFactory) {
        this.groupId = groupId;
        this.topicIds = topicIds;
        this.consumer = consumerFactory.createConsumer(groupId, groupId);
    }

    /**
     * 订阅
     */
    public void subscribe() {
        List<TopicPartition> topicPartitions = new ArrayList<>();
        for (String topicId : topicIds) {
            TopicPartition topicPartition = new TopicPartition(topicId, CommonConstant.NO);
            topicPartitions.add(topicPartition);
        }
//        consumer.subscribe(Arrays.asList(topicId));
        consumer.assign(topicPartitions);
        String threadName = Thread.currentThread().getName();
        while (active) {

            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            if (records.isEmpty()){
                continue;
            }

            ChannelHandlerContext channelHandlerContext = Constant.onlineUserMap.get(groupId);
            if (channelHandlerContext == null) {
                LOG.info("找不到用户连接, 取消用户订阅, uid:{}", groupId);
                close();
                continue;
            }

            for (ConsumerRecord<String, String> record : records) {
                LOG.info("GroupId = {}, thread = {},topic={} read offset ={}, key={} , value= {}, partition={}",
                        groupId, threadName, record.topic(), record.offset(), record.key(), record.value(), record.partition());

                //TODO 暂时直接调用消息推送 后期调用netty
                channelHandlerContext.writeAndFlush(new TextWebSocketFrame( record.value()));
            }
        }
    }


    /**
     * 用户下线取消订阅
     */
    public void close() {
        consumer.close();
        active = false;
    }
}
