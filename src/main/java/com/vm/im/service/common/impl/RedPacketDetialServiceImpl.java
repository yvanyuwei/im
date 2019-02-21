package com.vm.im.service.common.impl;

import com.vm.im.entity.common.RedPacketDetial;
import com.vm.im.dao.common.RedPacketDetialMapper;
import com.vm.im.service.common.RedPacketDetialService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 红包明细 服务实现类
 * </p>
 *
 * @author zhangqi
 * @since 2019-02-20
 */
@Service
public class RedPacketDetialServiceImpl extends ServiceImpl<RedPacketDetialMapper, RedPacketDetial> implements RedPacketDetialService {
    private static final Logger LOG = LoggerFactory.getLogger(RedPacketDetialServiceImpl.class);
}
