package com.vm.im.service.group.impl;

import com.vm.im.entity.group.ChatGroupNotice;
import com.vm.im.dao.group.ChatGroupNoticeMapper;
import com.vm.im.service.group.ChatGroupNoticeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 群公告表 服务实现类
 * </p>
 *
 * @author zhangqi
 * @since 2019-02-20
 */
@Service
public class ChatGroupNoticeServiceImpl extends ServiceImpl<ChatGroupNoticeMapper, ChatGroupNotice> implements ChatGroupNoticeService {
    private static final Logger LOG = LoggerFactory.getLogger(ChatGroupNoticeServiceImpl.class);
}
