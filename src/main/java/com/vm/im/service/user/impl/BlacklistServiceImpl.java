package com.vm.im.service.user.impl;

import com.vm.im.entity.user.Blacklist;
import com.vm.im.dao.user.BlacklistMapper;
import com.vm.im.service.user.BlacklistService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户操作流水记录表 服务实现类
 * </p>
 *
 * @author zhangqi
 * @since 2019-02-19
 */
@Service
public class BlacklistServiceImpl extends ServiceImpl<BlacklistMapper, Blacklist> implements BlacklistService {
    private static final Logger LOG = LoggerFactory.getLogger(BlacklistServiceImpl.class);
}
