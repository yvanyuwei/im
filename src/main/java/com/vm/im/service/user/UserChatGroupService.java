package com.vm.im.service.user;

import com.vm.im.common.vo.user.FindUserVO;
import com.alibaba.fastjson.JSONObject;
import com.vm.im.common.vo.user.UserChatVO;
import com.vm.im.entity.group.ChatGroup;
import com.vm.im.entity.user.UserChatGroup;
import com.baomidou.mybatisplus.extension.service.IService;
import io.netty.channel.ChannelHandlerContext;
import org.apache.ibatis.annotations.Param;

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

    /**
     * 模糊查找用户
     *
     * @param uid
     * @param targetId
     * @param condition
     * @return
     */
    List<FindUserVO> findUser(String uid, String targetId, String condition);

    /**
     * Query groupList information based on user ID
     * @param param
     * @param ctx
     */
    void userGroupList(JSONObject param, ChannelHandlerContext ctx);

    /**
     *                  根据ID查询聊天组列表信息
     * @param userId    id
     * @return          返回当前id的列表
     */
    List<UserChatGroup> selectByUserId(String userId);

    /**
     *                  更新用户信息
     * @param name      需要更新的昵称
     * @param groupId   工会id
     * @param nickname  备注的名称/名称
     */
    void updateUserMessage(String name, String groupId, String nickname);

    /**
     *                  加载群用户列表
     * @param param
     * @param ctx
     */
    void loadGroupUser(JSONObject param, ChannelHandlerContext ctx);

    /**
     *                  刷新群组信息
     * @param groupId
     */
    void flushGroupMsg(String groupId);

    List<String> selectGroupIdByUid(String userId);

    List<UserChatVO> selectByPrimaryKey(String groupId);
}
