package com.vm.im.service.common.impl;

import com.vm.im.entity.common.Level;
import com.vm.im.dao.common.LevelMapper;
import com.vm.im.service.common.LevelService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户群组等级 服务实现类
 * </p>
 *
 * @author zhangqi
 * @since 2019-02-20
 */
@Service
public class LevelServiceImpl extends ServiceImpl<LevelMapper, Level> implements LevelService {
    private static final Logger LOG = LoggerFactory.getLogger(LevelServiceImpl.class);
}
