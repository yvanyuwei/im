package com.vm.im.service.user;

import com.vm.im.common.vo.user.FindUserVO;
import com.alibaba.fastjson.JSONObject;
import com.vm.im.entity.user.UserFriend;
import com.baomidou.mybatisplus.extension.service.IService;
import io.netty.channel.ChannelHandlerContext;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户好友 服务类
 * </p>
 *
 * @author zhangqi
 * @since 2019-02-19
 */
@Service
public interface UserFriendService extends IService<UserFriend> {

    /**
     * 查询用户好友列表
     * @param param
     * @param ctx
     */
    void selectUserFriend(JSONObject param, ChannelHandlerContext ctx);

    /**
     * 模糊查找用户
     *
     * @param uid
     * @param condition
     * @return
     */
    List<FindUserVO> findUser(String uid, String condition);

    /**
     *                  更新用户信息
     * @param name      需要更新的昵称
     * @param friend_id 好友id
     * @param nickname  给好友备注的名称/好友昵称
     */
    void updateUserMessage(String name, String friend_id, String nickname);

    /**
     *                  根据好友ID查询好友列表信息
     * @param friendId  好友id
     * @param delFlag   状态情况
     * @return          返回当前好友id的列表
     */
    List<UserFriend> selectByFriendId(String friendId, Integer delFlag);
}
