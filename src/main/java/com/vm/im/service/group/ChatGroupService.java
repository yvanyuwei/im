package com.vm.im.service.group;

import com.vm.im.common.dto.admin.AuthOperationDTO;
import com.vm.im.common.dto.admin.GroupInfoDTO;
import com.vm.im.common.dto.admin.MemberOperationDTO;
import com.vm.im.common.dto.admin.UnionOperationDTO;
import com.vm.im.common.enums.BusinessTypeEnum;
import com.vm.im.common.enums.GroupRoleEnum;
import com.vm.im.entity.group.ChatGroup;
import com.baomidou.mybatisplus.extension.service.IService;
import com.vm.im.entity.user.User;
import com.vm.im.entity.user.UserChatGroup;

import java.util.List;

/**
 * <p>
 * 聊天群 服务类
 * </p>
 *
 * @author zhangqi
 * @since 2019-02-20
 */
public interface ChatGroupService extends IService<ChatGroup> {

    /**
     * 创建公会群
     *
     * @param unionOperationDTO
     */
    void createUnionGroup(UnionOperationDTO unionOperationDTO);

    /**
     * 删除公会群
     *
     * @param unionOperationDTO
     */
    void deleteUnionGroup(UnionOperationDTO unionOperationDTO);

    /**
     * 创建群
     *
     * @param chatGroup
     */
    void createGroup(ChatGroup chatGroup, User user);

    /**
     * 删除群
     *
     * @param chatGroup
     */
    void deleteGroup(ChatGroup chatGroup);

    /**
     * 工会添加成员
     *
     * @param memberOperationDTO
     */
    void addUnionMember(MemberOperationDTO memberOperationDTO);

    /**
     * 工会删除成员
     *
     * @param memberOperationDTO
     */
    void deleteUnionMember(MemberOperationDTO memberOperationDTO);

    /**
     * 添加工会用户权限为管理员
     *
     * @param authOperationDTO
     */
    void addUnionMemberAuth(AuthOperationDTO authOperationDTO);

    /**
     * 取消工会用户管理员权限
     *
     * @param authOperationDTO
     */
    void deleteUnionMemberAuth(AuthOperationDTO authOperationDTO);

    /**
     * 校验公会群状态及有效性
     * 群成员状态及有效性
     *
     * @param groupId
     * @param userId
     * @param roleEnum
     */
    UserChatGroup checkUnionGroupAndMember(String groupId, String userId, GroupRoleEnum roleEnum);

    /**
     * Query group information based on group ID
     *
     * @param groupId GroupId
     * @return Group user list
     */
    List<UserChatGroup> getByGroupId(String groupId);

    /**
     * Load information for Groups
     */
    void loadGroupInfo();

    String selectNameByGroupId(String groupId);

    /**
     * 加载指定工会信息到内存
     *
     * @param groupId
     */
    void addGroupInfo(String groupId);

    /**
     * 删除系统内存中的群组信息
     *
     * @param groupId
     */
    void delGroupInfo(String groupId);

    /**
     * 断开指定用户 或 群组用户的socket
     *
     * @param id  typeEnum 为用户 id就是用户id, typeEnum 为群组 id就是群组id
     * @param typeEnum
     */
    void closeConnection(String id, BusinessTypeEnum typeEnum);

    /**
     * 添加用户信息到群组系统内存
     *
     * @param groupId
     * @param uid
     */
    void addUserInfo(String groupId, String uid);

    /**
     * 删除系统内存群组信息的用户信息
     *
     * @param groupId
     * @param uid
     */
    void delUserInfo(String groupId, String uid);

    /**
     * 更新群组信息
     *
     * @param groupInfoDTO
     */
    void updateGroupInfo(GroupInfoDTO groupInfoDTO);

    /**
     * 校验用户聊天群信息
     *  @param userId
     * @param groupId
     */
    UserChatGroup checkChatGroup(String userId, String groupId);
}
