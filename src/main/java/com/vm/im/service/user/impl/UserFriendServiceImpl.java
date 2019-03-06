package com.vm.im.service.user.impl;

import com.alibaba.fastjson.JSON;
import com.vm.im.common.dto.ResultBean;
import com.vm.im.common.enums.ResultCodeEnum;
import com.vm.im.common.vo.user.FindUserVO;
import com.alibaba.fastjson.JSONObject;
import com.vm.im.common.constant.CommonConstant;
import com.vm.im.common.enums.ChatTypeEnum;
import com.vm.im.common.util.ResponseJson;
import com.vm.im.common.vo.user.UserMsgVO;
import com.vm.im.controller.aop.NeedUserAuth;
import com.vm.im.entity.user.UserFriend;
import com.vm.im.dao.user.UserFriendMapper;
import com.vm.im.netty.Constant;
import com.vm.im.service.user.UserFriendService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    public void selectUserFriend(JSONObject param, ChannelHandlerContext ctx){
        String userId = String.valueOf(param.get("userId"));
        while(Constant.onlineUserMap.get(userId) != null) {
            List<UserMsgVO> userFriends = userFriendMapper.selectByPrimaryKey(String.valueOf(param.get("userId")), String.valueOf(param.get("friendId")));
            //new ResultBean(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.name(), null)
        /*String responseJson = JSON.toJSONString(new ResultBean(ResultCodeEnum.SUCCESS.getCode(),
                ResultCodeEnum.SUCCESS.name(),null));*/
            String responseJson = new ResponseJson().success()
                    .setData("type", ChatTypeEnum.USER_FRIEND_LIST)
                    .setData("content", userFriends)
                    .toString();
            ctx.channel().writeAndFlush(new TextWebSocketFrame(responseJson));
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void updateUserMessage(String name, String friend_id, String nickname) {
        userFriendMapper.updateUserMessage(name,friend_id,nickname);
    }

    @Override
    public List<UserFriend> selectByFriendId(String friendId, Integer delFlag) {
        return userFriendMapper.selectByFriendId(friendId,CommonConstant.NO);
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
        return userFriendMapper.findUser(uid, condition);
    }
}
