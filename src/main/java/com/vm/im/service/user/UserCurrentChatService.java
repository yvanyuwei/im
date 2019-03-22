package com.vm.im.service.user;

import com.alibaba.fastjson.JSONObject;
import com.vm.im.common.dto.admin.GroupInfoDTO;
import com.vm.im.common.vo.user.FindUserVO;
import com.vm.im.entity.group.ChatGroup;
import com.vm.im.entity.user.User;
import com.vm.im.entity.user.UserCurrentChat;
import com.baomidou.mybatisplus.extension.service.IService;
import io.netty.channel.ChannelHandlerContext;

import java.util.List;

/**
 * <p>
 * 用户当前会话 服务类
 * </p>
 *
 * @author zhangqi
 * @since 2019-03-02
 */
public interface UserCurrentChatService extends IService<UserCurrentChat> {

    /**
     * 根据用户id 查询当前会话列表
     * @param param
     * @param ctx
     */
    void listByUid(JSONObject param, ChannelHandlerContext ctx);

    /**
     * 模糊查找用户
     *
     * @param uid
     * @param condition
     * @return
     */
    List<FindUserVO> findUser(String uid, String condition);

    /**
     * 刷新用户数据
     *
     * @param fromuser
     * @param toUser
     * @param count
     * @param param
     */
    void flushCurrentMsgListForUser(User fromuser,User toUser,int count, JSONObject param);

    /**
     * 通过好友Id查询用户当前会话列表
     * @param friendId
     * @return
     */
    List<UserCurrentChat> selectByFriendId(String friendId);

    /**
     * 更新用户数据
     * @param name
     * @param friendId
     * @param nickname
     */
    void updateUserMessage(String name, String friendId, String nickname);

    void CurrentMsgListForUser(User fromUser,User toUser,int count,String content);

    void CurrentMsgListForGroup(int count,String content,JSONObject param);

    List<String> findUidByFriendId(String friendId);

    /**
     * 清空指定群组所有成员的当前会话
     *
     * @param chatGroup
     */
    void clearUserCurrentChat(ChatGroup chatGroup);

    /**
     * 清空指定用户的当前会话
     *
     * @param userId
     */
    void clearUserCurrentChat(String userId);

    /**
     * 更新当前会话的群组昵称
     *
     * @param groupInfoDTO
     */
    void updateNickName(GroupInfoDTO groupInfoDTO);
}
