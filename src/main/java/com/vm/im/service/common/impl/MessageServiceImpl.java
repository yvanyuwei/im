package com.vm.im.service.common.impl;

import com.alibaba.fastjson.JSONObject;
import com.vm.im.common.enums.ChatTypeEnum;
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

    @Async
    public void saveMessage(JSONObject param,Long createTime) {
        Message msg = new Message();
        msg.setCreateTime(new Date(createTime));
        msg.setFromId(String.valueOf(param.get("fromUserId")));
        String type = String.valueOf(param.get("type"));
        if (type.equals(ChatTypeEnum.SINGLE_SENDING.name())){
            String toUserId = String.valueOf(param.get("toUserId"));
            msg.setToId(toUserId);
            msg.setType(1);
        }else if(type.equals(ChatTypeEnum.GROUP_SENDING.name())){
            String toGroupId = String.valueOf(param.get("toGroupId"));
            msg.setToId(toGroupId);
            msg.setType(3);
        }
        String content = String.valueOf(param.get("content"));
        if (content.length() > 1000){
            content = content.substring(0,1000);
        }
        msg.setContent(content);
        //msg.setCreateTime(new Date(Long.valueOf(String.valueOf(param.get("createTime")))));
        save(msg);
        //messageMapper.(msg);
    }
}
