package com.vm.im.service.user.impl;

import com.alibaba.fastjson.JSONObject;
import com.vm.im.common.constant.CommonConstant;
import com.vm.im.common.dto.user.UserCurrentDTO;
import com.vm.im.common.enums.ChatTypeEnum;
import com.vm.im.common.exception.BusinessException;
import com.vm.im.common.util.ResponseJson;
import com.vm.im.common.vo.user.FindCurrentVO;
import com.vm.im.common.vo.user.FindUserVO;
import com.vm.im.entity.group.ChatGroup;
import com.vm.im.entity.user.User;
import com.vm.im.entity.user.UserChatGroup;
import com.vm.im.entity.user.UserCurrentChat;
import com.vm.im.dao.user.UserCurrentChatMapper;
import com.vm.im.netty.Constant;
import com.vm.im.service.Redis.RedisService;
import com.vm.im.service.user.UserChatGroupService;
import com.vm.im.service.user.UserCurrentChatService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Autowired
    private RedisService redisService;

    @Autowired
    private UserChatGroupService userChatGroupService;


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
     */
    @Override
    @Async
    public void flushCurrentMsgListForUser(User fromUser,User toUser, int count,JSONObject param) {
        String content = String.valueOf(param.get("content"));
        if (content.length() > 1000){
            content = content.substring(0,1000);
        }
        if (param.get("type").equals(ChatTypeEnum.SINGLE_SENDING.name())) {
            CurrentMsgListForUser(fromUser,toUser,count,content);

        }else if(param.get("type").equals(ChatTypeEnum.GROUP_SENDING.name())){
            CurrentMsgListForGroup(count,content,param);

        }
    }

    @Override
    public void CurrentMsgListForUser(User fromUser,User toUser,int count,String content) {
        ChannelHandlerContext ctx = Constant.onlineUserMap.get(fromUser.getId());
        ChannelHandlerContext toCtx = Constant.onlineUserMap.get(toUser.getId());
        UserCurrentDTO userCurrentUid = new UserCurrentDTO();
        userCurrentUid.setUid(fromUser.getId());
        userCurrentUid.setFriendId(toUser.getId());
        userCurrentUid.setNickName(toUser.getName());
        userCurrentUid.setType(1);
        userCurrentUid.setLastMessage(content);
        UserCurrentChat userChatUid = buildUserCurrentChat(userCurrentUid);
        userCurrentChatMapper.saveOrUpdate(userChatUid);
        //LOG.info("插入数据为" + JSON.toJSONString(userChatUid));
        if (ctx != null) {
            JSONObject params = new JSONObject();
            params.put("userId", fromUser.getId());
            params.put("count", count);
            listByUid(params, ctx);
        }
        UserCurrentDTO userCurrentFid = new UserCurrentDTO();
        userCurrentFid.setUid(toUser.getId());
        userCurrentFid.setFriendId(fromUser.getId());
        userCurrentFid.setType(1);
        userCurrentFid.setNickName(fromUser.getName());
        userCurrentFid.setLastMessage(content);
        UserCurrentChat userChatFid = buildUserCurrentChat(userCurrentFid);
        userCurrentChatMapper.saveOrUpdate(userChatFid);
        //LOG.info("反插入数据为" + JSON.toJSONString(userChatFid));
        if (toCtx != null) {
            JSONObject params = new JSONObject();
            params.put("userId", toUser.getId());
            params.put("count", count);
            listByUid(params, toCtx);
        }
    }

    @Override
    public void CurrentMsgListForGroup(int count,String content,JSONObject param) {
        String groupId = String.valueOf(param.get("toGroupId"));
        //List<UserChatGroup> userChatGroups = Constant.groupInfoMap.get(groupId);
        List<String> uids = userChatGroupService.selectUidByGroupId(groupId);
        ChatGroup chatGroup = new ChatGroup();
        try {
            chatGroup = redisService.getRedisGroupMsgByGId(groupId);
        }catch (BusinessException busExp) {
            LOG.info("获取工会信息异常", busExp.getFailReason());
        }
        //String groupName = chatGroupService.selectNameByGroupId(groupId);
        List<UserCurrentChat> list = new ArrayList<>();
        for (String uid : uids) {
            UserCurrentDTO userCurrentGid = new UserCurrentDTO();
            userCurrentGid.setUid(uid);
            userCurrentGid.setFriendId(groupId);
            userCurrentGid.setNickName(chatGroup.getName());
            userCurrentGid.setType(3);
            userCurrentGid.setLastMessage(content);
            UserCurrentChat userChatGid = buildUserCurrentChat(userCurrentGid);
            list.add(userChatGid);
            //LOG.info("工会消息插入数据为:" + JSON.toJSONString(userChatGid));
        }
        userCurrentChatMapper.saveOrUpdateBatch(list);
        for (String uid : uids) {
            ChannelHandlerContext ctx = Constant.onlineUserMap.get(uid);
            if (ctx != null) {
                JSONObject params = new JSONObject();
                params.put("userId", uid);
                params.put("count", count);
                listByUid(params, ctx);
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
     * 清空指定群组所有成员的当前会话
     *
     * @param chatGroup
     */
    @Override
    public void clearUserCurrentChat(ChatGroup chatGroup) {
        userCurrentChatMapper.clearUserCurrentChatByGroupId(chatGroup.getId());
    }

    /**
     * 清空指定用户的当前会话
     *
     * @param userId
     */
    @Override
    public void clearUserCurrentChat(String userId) {
        userCurrentChatMapper.clearUserCurrentChat(userId);
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
