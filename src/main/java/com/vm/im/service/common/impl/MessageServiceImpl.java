package com.vm.im.service.common.impl;

import com.vm.im.entity.common.Message;
import com.vm.im.dao.common.MessageMapper;
import com.vm.im.service.common.MessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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
}
