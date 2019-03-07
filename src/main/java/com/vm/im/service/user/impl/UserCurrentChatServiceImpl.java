package com.vm.im.service.user.impl;

import com.alibaba.fastjson.JSONObject;
import com.vm.im.common.constant.CommonConstant;
import com.vm.im.common.dto.user.UserCurrentDTO;
import com.vm.im.common.enums.ChatTypeEnum;
import com.vm.im.common.util.ResponseJson;
import com.vm.im.common.vo.user.FindCurrentVO;
import com.vm.im.common.vo.user.FindUserVO;
import com.vm.im.entity.user.UserCurrentChat;
import com.vm.im.dao.user.UserCurrentChatMapper;
import com.vm.im.netty.Constant;
import com.vm.im.service.user.UserCurrentChatService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户当前会话 服务实现类
 * </p>
 *
 * @author zhangqi
 * @since 2019-03-02
 */
@Service
public class UserCurrentChatServiceImpl extends ServiceImpl<UserCurrentChatMapper, UserCurrentChat> implements UserCurrentChatService {
    private static final Logger LOG = LoggerFactory.getLogger(UserCurrentChatServiceImpl.class);

    @Autowired
    private UserCurrentChatMapper userCurrentChatMapper;

    /**
     * 根据用户id 查询当前会话列表
     * @param param
     * @param ctx
     */
    @Override
    public void listByUid(JSONObject param, ChannelHandlerContext ctx) {
        String uid = String.valueOf(param.get("userId"));
        int count = Integer.parseInt(String.valueOf(param.get("count")));
        if(Constant.onlineUserMap.get(uid) != null) {
            List<FindCurrentVO> userCurrentChats = userCurrentChatMapper.listByUid(uid, count);
            String responseJson = new ResponseJson().success()
                    .setData("type", ChatTypeEnum.USER_CURRENT_CHAT)
                    .setData("content", userCurrentChats)
                    .toString();
            ctx.channel().writeAndFlush(new TextWebSocketFrame(responseJson));
        }
    }

    /**
     * 加载刷新当前会话列表
     * @param userId
     */
    @Override
    public void flushCurrentMsgList(String userId, String friendId, int count,JSONObject param) {
        Map<String, ChannelHandlerContext> onlineUserMap = Constant.onlineUserMap;
        ChannelHandlerContext ctx = onlineUserMap.get(userId);
        ChannelHandlerContext toCtx = onlineUserMap.get(friendId);
        List<String> userIdList = userCurrentChatMapper.fingFriendByUid(userId);
        List<String> friendIdlist = userCurrentChatMapper.fingFriendByUid(friendId);
        if (ctx != null && !userIdList.contains(friendId)){
            UserCurrentDTO userCurrentDTO = new UserCurrentDTO();
            userCurrentDTO.setUid(userId);
            userCurrentDTO.setFriendId(friendId);
            userCurrentDTO.setLastMessage(String.valueOf(param.get("content")));
            UserCurrentChat userCurrentChat = buildUserCurrentChat(userCurrentDTO);
            save(userCurrentChat);
            JSONObject params = new JSONObject();
            params.put("userId",userId);
            params.put("count",count);
            listByUid(params,ctx);
        }
        if (toCtx != null && !friendIdlist.contains(userId)){
            UserCurrentDTO userCurrentDTO = new UserCurrentDTO();
            userCurrentDTO.setUid(friendId);
            userCurrentDTO.setFriendId(userId);
            userCurrentDTO.setLastMessage(String.valueOf(param.get("content")));
            UserCurrentChat userCurrentChat = buildUserCurrentChat(userCurrentDTO);
            save(userCurrentChat);
            JSONObject params = new JSONObject();
            params.put("userId",friendId);
            params.put("count",count);
            listByUid(params,toCtx);
        }
    }

    /**
     * 模糊查找用户
     *
     * @param uid
     * @param condition
     * @return
     */
    @Override
    public List<FindUserVO> findUser(String uid, String condition) {
        return userCurrentChatMapper.findUser(uid, condition);
    }

    /**
     * 构建一个userCurrentChat
     */
    public UserCurrentChat buildUserCurrentChat(UserCurrentDTO userCurrentDTO){
        UserCurrentChat userCurrentChat = new UserCurrentChat();
        userCurrentChat.setUserId(userCurrentDTO.getUid());
        userCurrentChat.setFriendId(userCurrentDTO.getFriendId());
        userCurrentChat.setType(CommonConstant.YES);
        userCurrentChat.setLastMessage(userCurrentDTO.getLastMessage());
        userCurrentChat.setDelFlag(CommonConstant.NO);
        userCurrentChat.setCreateTime(new Date());
        return userCurrentChat;
    }
}
