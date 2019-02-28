package com.vm.im.service.user.impl;

import com.alibaba.fastjson.JSON;
import com.vm.im.common.constant.CommonConstant;
import com.vm.im.common.enums.GroupRoleEnum;
import com.vm.im.entity.group.ChatGroup;
import com.vm.im.entity.user.UserChatGroup;
import com.vm.im.dao.user.UserChatGroupMapper;
import com.vm.im.service.group.ChatGroupFlowService;
import com.vm.im.service.group.ChatGroupOperationFlowService;
import com.vm.im.service.user.UserChatGroupService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import java.util.List;

/**
 * <p>
 * 用户群 服务实现类
 * </p>
 *
 * @author zhangqi
 * @since 2019-02-19
 */
@Service
public class UserChatGroupServiceImpl extends ServiceImpl<UserChatGroupMapper, UserChatGroup> implements UserChatGroupService {
    private static final Logger LOG = LoggerFactory.getLogger(UserChatGroupServiceImpl.class);

    @Autowired
    private UserChatGroupMapper userChatGroupMapper;

    @Autowired
    private ChatGroupFlowService chatGroupFlowService;

    @Autowired
    private ChatGroupOperationFlowService chatGroupOperationFlowService;

    /**
     * 添加群主加入指定群组
     *
     * @param chatGroup
     */
    @Override
    public void addMasterToGroup(ChatGroup chatGroup) {
        LOG.info("开始添加群主到群组");
        UserChatGroup userChatGroup = buildMasterChatGroup(chatGroup);

        saveOrUpdate(userChatGroup);
    }

    /**
     * 用户群组信息删除指定群(逻辑删除)
     *
     * @param groupId
     */
    @Override
    public void deleteUserChatGroup(String groupId) {
       LOG.info("开始逻辑删除用户群组信息");
       userChatGroupMapper.deleteByGroupId(groupId);
    }

    /**
     * 根据群id 用户id 查询群用户数据
     *
     * @param groupId
     * @param userId
     * @return
     */
    @Override
    public UserChatGroup selectUserByGroupIdAndUid(String groupId, String userId) {
        return userChatGroupMapper.selectUserByGroupIdAndUid(groupId, userId);
    }

    /**
     * 群组添加成员
     *
     * @param userChatGroup
     */
    @Override
    @Transactional
    public void addGroupMember(UserChatGroup userChatGroup) {
        //添加群成员数据
        saveOrUpdate(userChatGroup);
        //添加成员流水数据
        chatGroupFlowService.addChatGroupFlow(userChatGroup);

    }

    /**
     * 群组删除成员
     *
     * @param userChatGroup
     */
    @Override
    @Transactional
    public void deleteGroupMember(UserChatGroup userChatGroup) {
        //删除群成员数据
        userChatGroup.setDelFlag(CommonConstant.YES);
        saveOrUpdate(userChatGroup);
        //添加成员流水数据
        chatGroupFlowService.addChatGroupFlow(userChatGroup);
    }

    @Override
    @Transactional
    public void updateMemberAuth(UserChatGroup userChatGroup) {
        //更新用户角色
        saveOrUpdate(userChatGroup);
        //添加群组操作流水
        chatGroupOperationFlowService.addMemberAuthOperationFlow(userChatGroup);
    }

    /**
     * 构建群主群组信息
     *
     * @param chatGroup
     * @return
     */
    private UserChatGroup buildMasterChatGroup(ChatGroup chatGroup) {
        UserChatGroup userChatGroup = new UserChatGroup();
        userChatGroup.setUserId(chatGroup.getMaster());
        userChatGroup.setChatGroupId(chatGroup.getId());
        userChatGroup.setTop(CommonConstant.YES);
        userChatGroup.setType(GroupRoleEnum.MASTER.value());
        userChatGroup.setDelFlag(CommonConstant.NO);
        userChatGroup.setCreateTime(new Date());
        LOG.info("构建用户群组信息, userChatGroup:{}", JSON.toJSONString(userChatGroup));

        return userChatGroup;
    }
}
