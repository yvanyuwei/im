package com.vm.im.service.common.impl;

import com.alibaba.fastjson.JSONObject;
import com.vm.im.entity.common.Message;
import com.vm.im.dao.common.MessageMapper;
import com.vm.im.service.common.MessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.xml.bind.PrintConversionEvent;
import java.sql.Date;

/**
 * <p>
 * 信息表 服务实现类
 * </p>
 *
 * @author zhangqi
 * @since 2019-02-20
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {
    private static final Logger LOG = LoggerFactory.getLogger(MessageServiceImpl.class);

    @Autowired
    private MessageMapper messageMapper;

    @Async
    public void saveMessage(JSONObject param) {
        Message msg = new Message();
        msg.setFromId(String.valueOf(param.get("fromUserId")));
        msg.setToId(String.valueOf(param.get("toUserId")));
        msg.setContent(String.valueOf(param.get("content")));
        msg.setCreateTime(new Date(Long.valueOf(String.valueOf(param.get("createTime")))));
        if (param.get("toUserId") != null){
            msg.setType(1);
        }else if(param.get("toGroupId") != null){
            msg.setType(3);
        }
        messageMapper.insert(msg);
    }
}
