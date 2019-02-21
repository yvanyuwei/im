package com.vm.im.service.common.impl;

import com.vm.im.entity.common.RedPacket;
import com.vm.im.dao.common.RedPacketMapper;
import com.vm.im.service.common.RedPacketService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 红包 服务实现类
 * </p>
 *
 * @author zhangqi
 * @since 2019-02-20
 */
@Service
public class RedPacketServiceImpl extends ServiceImpl<RedPacketMapper, RedPacket> implements RedPacketService {
    private static final Logger LOG = LoggerFactory.getLogger(RedPacketServiceImpl.class);
}
