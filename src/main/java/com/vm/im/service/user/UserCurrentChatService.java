package com.vm.im.service.user;

import com.vm.im.common.vo.user.FindUserVO;
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
     *
     * @param uid   用户id
     * @param count list size
     * @return
     */
    void listByUid(String uid, int count, ChannelHandlerContext ctx);

    /**
     * 模糊查找用户
     *
     * @param uid
     * @param condition
     * @return
     */
    List<FindUserVO> findUser(String uid, String condition);
}
