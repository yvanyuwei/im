package com.vm.im.service.user.impl;

import com.vm.im.entity.user.Group;
import com.vm.im.dao.user.GroupMapper;
import com.vm.im.service.user.GroupService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 好友分组表 服务实现类
 * </p>
 *
 * @author zhangqi
 * @since 2019-02-19
 */
@Service
public class GroupServiceImpl extends ServiceImpl<GroupMapper, Group> implements GroupService {
    private static final Logger LOG = LoggerFactory.getLogger(GroupServiceImpl.class);
}
