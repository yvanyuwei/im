package com.vm.im.service.user;

import com.vm.im.entity.group.ChatGroup;
import com.vm.im.entity.user.UserChatGroup;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户群 服务类
 * </p>
 *
 * @author zhangqi
 * @since 2019-02-19
 */
public interface UserChatGroupService extends IService<UserChatGroup> {

    //List<UserChatGroup> getUserInfoByGroupId(String GroupId);
    /**
     * 添加群主加入指定群组
     *
     * @param chatGroup
     */
    void addMasterToGroup(ChatGroup chatGroup);

    /**
     * 用户群组信息删除指定群(逻辑删除)
     *
     * @param groupId
     */
    void deleteUserChatGroup(String groupId);

    /**
     * 根据群id 用户id 查询群用户数据
     *
     * @param groupId
     * @param userId
     * @return
     */
    UserChatGroup selectUserByGroupIdAndUid(String groupId, String userId);

    /**
     * 群组添加成员
     *
     * @param userChatGroup
     */
    void addGroupMember(UserChatGroup userChatGroup);

    /**
     * 群组删除成员
     *
     * @param userChatGroup
     */
    void deleteGroupMember(UserChatGroup userChatGroup);

    /**
     * 更新用户群组权限
     *
     * @param userChatGroup
     */
    void updateMemberAuth(UserChatGroup userChatGroup);
}
