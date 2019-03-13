package com.vm.im.service.user.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.vm.im.common.constant.CommonConstant;
import com.vm.im.common.dto.user.UserCurrentDTO;
import com.vm.im.common.enums.ChatTypeEnum;
import com.vm.im.common.util.ResponseJson;
import com.vm.im.common.vo.user.FindCurrentVO;
import com.vm.im.common.vo.user.FindUserVO;
import com.vm.im.entity.user.User;
import com.vm.im.entity.user.UserCurrentChat;
import com.vm.im.dao.user.UserCurrentChatMapper;
import com.vm.im.netty.Constant;
import com.vm.im.service.group.ChatGroupService;
import com.vm.im.service.user.UserChatGroupService;
import com.vm.im.service.user.UserCurrentChatService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vm.im.service.user.UserService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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

    @Lazy
    @Autowired
    private UserService userService;

    @Autowired
    private UserChatGroupService userChatGroupService;

    @Autowired
    private ChatGroupService chatGroupService;

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
            List<FindCurrentVO> userCurrentChats = userCurrentChatMapper.listByUid(uid,count);
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
    public void flushCurrentMsgListForUser(String userId, String friendId, int count,JSONObject param,User user) {
        //userService.saveUserInfo(user);
        if (param.get("type").equals(ChatTypeEnum.SINGLE_SENDING.name())) {
            ChannelHandlerContext ctx = Constant.onlineUserMap.get(userId);
            ChannelHandlerContext toCtx = Constant.onlineUserMap.get(friendId);
            /*List<String> userIdList = userCurrentChatMapper.findFriendByUid(userId);
            List<String> friendIdlist = userCurrentChatMapper.findFriendByUid(friendId);*/
            User friend = userService.getRedisUserById(friendId);
            UserCurrentDTO userCurrentUid = new UserCurrentDTO();
            userCurrentUid.setUid(userId);
            userCurrentUid.setFriendId(friendId);
            userCurrentUid.setNickName(friend.getName());
            userCurrentUid.setType(1);
            userCurrentUid.setLastMessage(String.valueOf(param.get("content")));
            UserCurrentChat userChatUid = buildUserCurrentChat(userCurrentUid);
            userCurrentChatMapper.saveOrUpdate(userChatUid);
            //userService.saveUserInfo(user);
            LOG.info("插入数据为" + JSON.toJSONString(userChatUid));
            if (ctx != null) {
                JSONObject params = new JSONObject();
                params.put("userId", userId);
                params.put("count", count);
                listByUid(params, ctx);
            }
            //User friend = userService.getRedisUserById(friendId);
            UserCurrentDTO userCurrentFid = new UserCurrentDTO();
            userCurrentFid.setUid(friendId);
            userCurrentFid.setFriendId(userId);
            userCurrentFid.setType(1);
            userCurrentFid.setNickName(user.getName());
            userCurrentFid.setLastMessage(String.valueOf(param.get("content")));
            UserCurrentChat userChatFid = buildUserCurrentChat(userCurrentFid);
            userCurrentChatMapper.saveOrUpdate(userChatFid);
            //userService.saveUserInfo(user);
            LOG.info("反插入数据为" + JSON.toJSONString(userChatFid));
            if (toCtx != null) {
                JSONObject params = new JSONObject();
                params.put("userId", friendId);
                params.put("count", count);
                listByUid(params, toCtx);
            }
        }else if(param.get("type").equals(ChatTypeEnum.GROUP_SENDING.name())){
            List<String> uids = userChatGroupService.selectUidByGroupId(friendId);
            String groupName = chatGroupService.selectNameByGroupId(friendId);
            for (String uid : uids) {
                ChannelHandlerContext ctx = Constant.onlineUserMap.get(uid);
                UserCurrentDTO userCurrentGid = new UserCurrentDTO();
                userCurrentGid.setUid(uid);
                userCurrentGid.setFriendId(friendId);
                userCurrentGid.setNickName(groupName);
                userCurrentGid.setType(3);
                userCurrentGid.setLastMessage(String.valueOf(param.get("content")));
                UserCurrentChat userChatGid = buildUserCurrentChat(userCurrentGid);
                userCurrentChatMapper.saveOrUpdate(userChatGid);
                //userService.saveUserInfo(user);
                LOG.info("工会消息插入数据为:" + JSON.toJSONString(userChatGid));
                if (ctx != null) {
                    JSONObject params = new JSONObject();
                    params.put("userId", userId);
                    params.put("count", count);
                    listByUid(params, ctx);
                }
            }
        }
    }

    @Override
    public List<UserCurrentChat> selectByFriendId(String friendId) {
        return userCurrentChatMapper.selectByFriendId(friendId);
    }

    @Override
    public void updateUserMessage(String name, String friendId, String nickname) {
        userCurrentChatMapper.updateUserMessage(name,friendId,nickname);
    }

    @Override
    public List<String> findUidByFriendId(String friendId) {
        return userCurrentChatMapper.findUidByFriendId(friendId);
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
        userCurrentChat.setNickname(userCurrentDTO.getNickName());
        userCurrentChat.setType(userCurrentDTO.getType());
        userCurrentChat.setLastMessage(userCurrentDTO.getLastMessage());
        userCurrentChat.setDelFlag(CommonConstant.NO);
        userCurrentChat.setCreateTime(new Date());
        return userCurrentChat;
    }
}
