package com.vm.im.service.user.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.vm.im.common.constant.CommonConstant;
import com.vm.im.common.dto.user.UserFriendListDTO;
import com.vm.im.common.enums.ChatTypeEnum;
import com.vm.im.common.util.ResponseJson;
import com.vm.im.controller.aop.NeedUserAuth;
import com.vm.im.entity.user.UserFriend;
import com.vm.im.dao.user.UserFriendMapper;
import com.vm.im.entity.user.UserToken;
import com.vm.im.service.user.UserFriendService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 用户好友 服务实现类
 * </p>
 *
 * @author zhangqi
 * @since 2019-02-19
 */
@Service
public class UserFriendServiceImpl extends ServiceImpl<UserFriendMapper, UserFriend> implements UserFriendService {
    private static final Logger LOG = LoggerFactory.getLogger(UserFriendServiceImpl.class);

    @Autowired
    private UserFriendMapper userFriendMapper;

    @Autowired
    private NeedUserAuth needUserAuth;

    public void selectUserFriend(JSONObject param, ChannelHandlerContext ctx){
        /*String userMsg = null;
        try {
            userMsg = needUserAuth.checkToken();
        } catch (IOException e) {
            // todo 异常
            LOG.error("==================");
        }*/
        //UserFriend userFriend = new UserFriend();
        //UserToken userToken = JSON.parseObject(userMsg, UserToken.class);
        List<UserFriend> userFriends = userFriendMapper.selectByPrimaryKey(String.valueOf(param.get("userId")), String.valueOf(param.get("friendId")), CommonConstant.NO);
        String responseJson = new ResponseJson().success()
                .setData("type", ChatTypeEnum.USER_FRIEND_LIST)
                .setData("content",userFriends)
                .toString();
        ctx.channel().writeAndFlush(new TextWebSocketFrame(responseJson));
    }
}
