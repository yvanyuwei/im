package com.vm.im.service.user;

import com.alibaba.fastjson.JSONObject;
import com.vm.im.common.vo.user.FindUserVO;
import com.vm.im.entity.user.UserCurrentChat;
import com.baomidou.mybatisplus.extension.service.IService;
import io.netty.channel.ChannelHandlerContext;
import org.apache.ibatis.annotations.Param;

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
     * @param userId
     */
    void flushCurrentMsgList(String userId,String friendId ,int count,JSONObject param);

    List<UserCurrentChat> selectByFriendId(String friendId);

    void updateUserMessage(String name, String friendId, String nickname);
}
