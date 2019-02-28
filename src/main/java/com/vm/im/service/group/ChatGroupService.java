package com.vm.im.service.group;

import com.vm.im.common.dto.admin.AuthOperationDTO;
import com.vm.im.common.dto.admin.MemberOperationDTO;
import com.vm.im.common.dto.admin.UnionOperationDTO;
import com.vm.im.common.enums.GroupRoleEnum;
import com.vm.im.entity.group.ChatGroup;
import com.baomidou.mybatisplus.extension.service.IService;
import com.vm.im.entity.user.UserChatGroup;

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
    void createGroup(ChatGroup chatGroup);

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
}
