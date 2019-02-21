package com.vm.im.service.user.impl;

import com.vm.im.entity.user.UserOperationFlow;
import com.vm.im.dao.user.UserOperationFlowMapper;
import com.vm.im.service.user.UserOperationFlowService;
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
public class UserOperationFlowServiceImpl extends ServiceImpl<UserOperationFlowMapper, UserOperationFlow> implements UserOperationFlowService {
    private static final Logger LOG = LoggerFactory.getLogger(UserOperationFlowServiceImpl.class);
}
