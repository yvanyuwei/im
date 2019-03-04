package com.vm.im.service.group.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.vm.im.common.constant.CommonConstant;
import com.vm.im.common.dto.admin.AuthOperationDTO;
import com.vm.im.common.dto.admin.MemberOperationDTO;
import com.vm.im.common.dto.admin.UnionOperationDTO;
import com.vm.im.common.enums.AdminRoleEnum;
import com.vm.im.common.enums.BusinessExceptionEnum;
import com.vm.im.common.enums.GroupLeverEnum;
import com.vm.im.common.enums.GroupRoleEnum;
import com.vm.im.common.exception.BusinessException;
import com.vm.im.common.util.StringUtil;
import com.vm.im.dao.user.UserChatGroupMapper;
import com.vm.im.entity.group.ChatGroup;
import com.vm.im.dao.group.ChatGroupMapper;
import com.vm.im.entity.user.UserChatGroup;
import com.vm.im.service.group.ChatGroupFlowService;
import com.vm.im.service.group.ChatGroupOperationFlowService;
import com.vm.im.netty.Constant;
import com.vm.im.common.util.StringUtil;
import com.vm.im.entity.group.ChatGroup;
import com.vm.im.dao.group.ChatGroupMapper;
import com.vm.im.entity.user.UserChatGroup;
import com.vm.im.service.group.ChatGroupFlowService;
import com.vm.im.service.group.ChatGroupOperationFlowService;
import com.vm.im.service.group.ChatGroupFlowService;
import com.vm.im.service.group.ChatGroupOperationFlowService;
import com.vm.im.netty.Constant;
import com.vm.im.service.group.ChatGroupService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vm.im.service.user.UserChatGroupService;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import java.util.List;

/**
 * <p>
 * 聊天群 服务实现类
 * </p>
 *
 * @author zhangqi
 * @since 2019-02-20
 */
@Service
public class ChatGroupServiceImpl extends ServiceImpl<ChatGroupMapper, ChatGroup> implements ChatGroupService {
    private static final Logger LOG = LoggerFactory.getLogger(ChatGroupServiceImpl.class);

    @Autowired
    private ChatGroupMapper chatGroupMapper;

    @Autowired
    private UserChatGroupMapper userChatGroupMapper;

    @Autowired
    private ChatGroupFlowService chatGroupFlowService;

    @Autowired
    private UserChatGroupService userChatGroupService;

    @Autowired
    private ChatGroupOperationFlowService chatGroupOperationFlowService;

    /**
     * 创建工会群
     *
     * @param unionOperationDTO
     */
    @Override
    public void createUnionGroup(UnionOperationDTO unionOperationDTO) {
        ChatGroup chatGroup = getById(unionOperationDTO.getGroupId());
        if (chatGroup != null && chatGroup.getDelFlag() == CommonConstant.NO) {
            LOG.info("群早已创建, 创建群失败, groupId:{}", unionOperationDTO.getGroupId());
            throw new BusinessException(BusinessExceptionEnum.GROUP_EXIST_EXCEPTION.getFailCode(), BusinessExceptionEnum.GROUP_EXIST_EXCEPTION.getFailReason());
        }

        ChatGroup group = buildChatGroup(unionOperationDTO);

        createGroup(group);

    }

    /**
     * 删除工会群
     *
     * @param unionOperationDTO
     */
    @Override
    public void deleteUnionGroup(UnionOperationDTO unionOperationDTO) {
        ChatGroup chatGroup = getById(unionOperationDTO.getGroupId());

        checkUnionGroupAndMember(unionOperationDTO.getGroupId(), unionOperationDTO.getUid(), GroupRoleEnum.MASTER);

        deleteGroup(chatGroup);
    }

    /**
     * 创建群
     *
     * @param chatGroup
     */
    @Override
    @Transactional
    public void createGroup(ChatGroup chatGroup) {
        //创建群
        saveOrUpdate(chatGroup);

        //添加群成员流水
        chatGroupFlowService.addGroupMaster(chatGroup);

        //添加用户群组数据
        userChatGroupService.addMasterToGroup(chatGroup);

    }

    /**
     * 删除群
     *
     * @param chatGroup
     */
    @Override
    @Transactional
    public void deleteGroup(ChatGroup chatGroup) {
        //更新群状态为删除
        chatGroup.setDelFlag(CommonConstant.YES);
        chatGroup.setUpdateTime(new Date());
        chatGroup.setUpdateBy(AdminRoleEnum.ADMIN.name());
        updateById(chatGroup);

        //更新用户群数据为删除
        userChatGroupService.deleteUserChatGroup(chatGroup.getId());

        //添加操作流水
        chatGroupOperationFlowService.addDeleteGroupFlow(chatGroup);

    }

    /**
     * 工会添加成员
     *
     * @param memberOperationDTO
     */
    @Override
    public void addUnionMember(MemberOperationDTO memberOperationDTO) {
        checkUnionGroupAndMember(memberOperationDTO.getGroupId(), null, null);

        UserChatGroup tepm = userChatGroupService.selectUserByGroupIdAndUid(memberOperationDTO.getGroupId(), memberOperationDTO.getUId());
        if (tepm != null && CommonConstant.NO == tepm.getDelFlag()) {
            LOG.info("用户早已添加该群, groupId:{}, uId:{}", memberOperationDTO.getGroupId(), memberOperationDTO.getUId());
            throw new BusinessException(BusinessExceptionEnum.GROUP_MEMBER_EXIST_EXCEPTION.getFailCode(), BusinessExceptionEnum.GROUP_MEMBER_EXIST_EXCEPTION.getFailReason());
        }

        UserChatGroup userChatGroup = bulidUserChatGroup(memberOperationDTO);

        userChatGroupService.addGroupMember(userChatGroup);
    }

    /**
     * 工会删除成员
     *
     * @param memberOperationDTO
     */
    @Override
    public void deleteUnionMember(MemberOperationDTO memberOperationDTO) {
        checkUnionGroupAndMember(memberOperationDTO.getGroupId(), null, null);

        UserChatGroup userChatGroup = userChatGroupService.selectUserByGroupIdAndUid(memberOperationDTO.getGroupId(), memberOperationDTO.getUId());
        if (userChatGroup == null || CommonConstant.YES == userChatGroup.getDelFlag()) {
            LOG.info("群组用户不存在, groupId:{}, uId:{}", memberOperationDTO.getGroupId(), memberOperationDTO.getUId());
            throw new BusinessException(BusinessExceptionEnum.GROUP_MEMBER_NOT_EXIST_EXCEPTION.getFailCode(), BusinessExceptionEnum.GROUP_MEMBER_NOT_EXIST_EXCEPTION.getFailReason());
        }

        userChatGroupService.deleteGroupMember(userChatGroup);
    }

    /**
     * 添加工会用户权限为管理员
     *
     * @param authOperationDTO
     */
    @Override
    public void addUnionMemberAuth(AuthOperationDTO authOperationDTO) {
        UserChatGroup userChatGroup = checkUnionGroupAndMember(authOperationDTO.getGroupId(), authOperationDTO.getUid(), GroupRoleEnum.USER);

        userChatGroup.setType(GroupRoleEnum.ADMIN.value());
        userChatGroupService.updateMemberAuth(userChatGroup);
    }

    /**
     * 取消工会用户管理员权限
     *
     * @param authOperationDTO
     */
    @Override
    public void deleteUnionMemberAuth(AuthOperationDTO authOperationDTO) {
        UserChatGroup userChatGroup = checkUnionGroupAndMember(authOperationDTO.getGroupId(), authOperationDTO.getUid(), GroupRoleEnum.ADMIN);

        userChatGroup.setType(GroupRoleEnum.USER.value());
        userChatGroupService.updateMemberAuth(userChatGroup);
    }

    /**
     * 校验公会群状态及有效性
     * 群成员状态及有效性
     *
     * @param groupId
     * @param userId   userId 为null时只校验群状态
     * @param roleEnum roleEnum 为null时只校验是否存在不校验用户角色
     */
    @Override
    public UserChatGroup checkUnionGroupAndMember(String groupId, String userId, GroupRoleEnum roleEnum) {
        ChatGroup chatGroup = getById(groupId);
        if (chatGroup == null || chatGroup.getDelFlag() == CommonConstant.YES || chatGroup.getType() != GroupLeverEnum.UNION.value()) {
            LOG.info("群组不存在或非工会群, groupId:{}", groupId);
            throw new BusinessException(BusinessExceptionEnum.GROUP_NOT_FOUND_EXCEPTION.getFailCode(), BusinessExceptionEnum.GROUP_NOT_FOUND_EXCEPTION.getFailReason());
        }

        UserChatGroup result = null;
        if (StringUtil.isNotEmpty(userId)) {
            if (null == roleEnum) {
                UserChatGroup userChatGroup = userChatGroupService.selectUserByGroupIdAndUid(groupId, userId);
                if (userChatGroup == null || CommonConstant.YES == userChatGroup.getDelFlag()) {
                    LOG.info("群组不存在此成员, groupId:{}, userId:{}", groupId, userId);
                    throw new BusinessException(BusinessExceptionEnum.GROUP_AUTH_EXCEPTION.getFailCode(), BusinessExceptionEnum.GROUP_AUTH_EXCEPTION.getFailReason());
                }
                result = userChatGroup;
            }

            if (GroupRoleEnum.MASTER == roleEnum) {
                UserChatGroup userChatGroup = userChatGroupService.selectUserByGroupIdAndUid(groupId, userId);
                if (userChatGroup == null || CommonConstant.YES == userChatGroup.getDelFlag()) {
                    LOG.info("群组不存在此成员, groupId:{}, userId:{}", groupId, userId);
                    throw new BusinessException(BusinessExceptionEnum.GROUP_AUTH_EXCEPTION.getFailCode(), BusinessExceptionEnum.GROUP_AUTH_EXCEPTION.getFailReason());
                }

                if (GroupRoleEnum.MASTER.value() != userChatGroup.getType()) {
                    LOG.info("申请用户不是群主, groupId:{}, userId:{}", groupId, userId);
                    throw new BusinessException(BusinessExceptionEnum.GROUP_AUTH_EXCEPTION.getFailCode(), BusinessExceptionEnum.GROUP_AUTH_EXCEPTION.getFailReason());
                }
                result = userChatGroup;
            }

            if (GroupRoleEnum.ADMIN == roleEnum) {
                UserChatGroup userChatGroup = userChatGroupService.selectUserByGroupIdAndUid(groupId, userId);
                if (userChatGroup == null || CommonConstant.YES == userChatGroup.getDelFlag()) {
                    LOG.info("群组不存在此成员, groupId:{}, userId:{}", groupId, userId);
                    throw new BusinessException(BusinessExceptionEnum.GROUP_AUTH_EXCEPTION.getFailCode(), BusinessExceptionEnum.GROUP_AUTH_EXCEPTION.getFailReason());
                }

                if (GroupRoleEnum.ADMIN.value() != userChatGroup.getType()) {
                    LOG.info("此成员未拥有管理员权限, groupId:{}, userId:{}", groupId, userId);
                    throw new BusinessException(BusinessExceptionEnum.GROUP_AUTH_EXCEPTION.getFailCode(), BusinessExceptionEnum.GROUP_AUTH_EXCEPTION.getFailReason());
                }
                result = userChatGroup;
            }

            if (GroupRoleEnum.USER == roleEnum) {
                UserChatGroup userChatGroup = userChatGroupService.selectUserByGroupIdAndUid(groupId, userId);
                if (userChatGroup == null || CommonConstant.YES == userChatGroup.getDelFlag()) {
                    LOG.info("群组不存在此成员, groupId:{}, userId:{}", groupId, userId);
                    throw new BusinessException(BusinessExceptionEnum.GROUP_AUTH_EXCEPTION.getFailCode(), BusinessExceptionEnum.GROUP_AUTH_EXCEPTION.getFailReason());
                }

                if (GroupRoleEnum.USER.value() != userChatGroup.getType()) {
                    LOG.info("此成员不是群普通成员, groupId:{}, userId:{}", groupId, userId);
                    throw new BusinessException(BusinessExceptionEnum.GROUP_AUTH_EXCEPTION.getFailCode(), BusinessExceptionEnum.GROUP_AUTH_EXCEPTION.getFailReason());
                }
                result = userChatGroup;
            }
        }
        return result;
    }

    @Override
    public ChatGroup checkGroup(UnionOperationDTO unionOperationDTO) {
        return null;
    }

    /**
     * 构建用户群组信息
     *
     * @param memberOperationDTO
     * @return
     */
    public UserChatGroup bulidUserChatGroup(MemberOperationDTO memberOperationDTO) {
        UserChatGroup userChatGroup = new UserChatGroup();
        userChatGroup.setUserId(memberOperationDTO.getUId());
        userChatGroup.setChatGroupId(memberOperationDTO.getGroupId());
        userChatGroup.setTop(CommonConstant.YES);
        userChatGroup.setType(GroupRoleEnum.USER.value());
        userChatGroup.setCanSpeak(CommonConstant.YES);
        userChatGroup.setDelFlag(CommonConstant.NO);
        userChatGroup.setCreateTime(new Date());
        LOG.info("构建用户群组信息, userChatGroup:{}", JSON.toJSONString(userChatGroup));

        return userChatGroup;
    }

    /**
     * 构建群组
     *
     * @param unionOperationDTO
     * @return
     */
    private ChatGroup buildChatGroup(UnionOperationDTO unionOperationDTO) {
        ChatGroup chatGroup = new ChatGroup();
        chatGroup.setId(unionOperationDTO.getGroupId());
        chatGroup.setName(unionOperationDTO.getGroupName());
        chatGroup.setMaster(unionOperationDTO.getUid());
        chatGroup.setType(GroupLeverEnum.UNION.value());
        chatGroup.setAvatar(unionOperationDTO.getAvatar());
        chatGroup.setCreateTime(new Date());
        chatGroup.setCreateBy(AdminRoleEnum.ADMIN.name());
        LOG.info("构建群组信息, chatGroup:{}", JSON.toJSONString(chatGroup));

        return chatGroup;
    }

    @Override
    public List<UserChatGroup> getByGroupId(String groupId) {
        return Constant.groupInfoMap.get(groupId);
    }

    @Override
    public void loadGroupInfo() {
        List<ChatGroup> chatGroups = chatGroupMapper.selectAllGroup();
        for (ChatGroup chatGroup : chatGroups) {
            List<UserChatGroup> userChatGroups = userChatGroupMapper.selectByGroupId(chatGroup.getId());
            Constant.groupInfoMap.put(chatGroup.getId(),userChatGroups);
        }
    }


}
