package com.vm.im.service.user.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.vm.im.common.constant.CommonConstant;
import com.vm.im.common.enums.BusinessExceptionEnum;
import com.vm.im.common.enums.ChatTypeEnum;
import com.vm.im.common.enums.GroupRoleEnum;
import com.vm.im.common.exception.BusinessException;
import com.vm.im.common.vo.user.FindUserVO;
import com.vm.im.common.util.ResponseJson;
import com.vm.im.common.vo.user.UserChatGroupVO;
import com.vm.im.common.vo.user.UserChatVO;
import com.vm.im.entity.group.ChatGroup;
import com.vm.im.entity.user.UserChatGroup;
import com.vm.im.dao.user.UserChatGroupMapper;
import com.vm.im.netty.Constant;
import com.vm.im.service.group.ChatGroupFlowService;
import com.vm.im.service.group.ChatGroupOperationFlowService;
import com.vm.im.service.group.ChatGroupService;
import com.vm.im.service.user.UserChatGroupService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户群 服务实现类
 * </p>
 *
 * @author zhangqi
 * @since 2019-02-19
 */
@Service
public class UserChatGroupServiceImpl extends ServiceImpl<UserChatGroupMapper, UserChatGroup> implements UserChatGroupService {
    private static final Logger LOG = LoggerFactory.getLogger(UserChatGroupServiceImpl.class);

    @Autowired
    private UserChatGroupMapper userChatGroupMapper;

    @Autowired
    private ChatGroupFlowService chatGroupFlowService;

    @Autowired
    private ChatGroupOperationFlowService chatGroupOperationFlowService;

    @Autowired
    private ChatGroupService chatGroupService;

    /**
     * 添加群主加入指定群组
     *
     * @param chatGroup
     */
    @Override
    public void addMasterToGroup(ChatGroup chatGroup) {
        LOG.info("开始添加群主到群组");
        UserChatGroup userChatGroup = buildMasterChatGroup(chatGroup);

        saveOrUpdate(userChatGroup);
    }

    /**
     * 用户群组信息删除指定群(逻辑删除)
     *
     * @param groupId
     */
    @Override
    public void deleteUserChatGroup(String groupId) {
       LOG.info("开始逻辑删除用户群组信息");
       userChatGroupMapper.deleteByGroupId(groupId);
    }

    /**
     * 根据群id 用户id 查询群用户数据
     *
     * @param groupId
     * @param userId
     * @return
     */
    @Override
    public UserChatGroup selectUserByGroupIdAndUid(String groupId, String userId) {
        return userChatGroupMapper.selectUserByGroupIdAndUid(groupId, userId);
    }

    /**
     * 群组添加成员
     *
     * @param userChatGroup
     */
    @Override
    @Transactional
    public void addGroupMember(UserChatGroup userChatGroup) {
        //添加群成员数据
        saveOrUpdate(userChatGroup);
        //添加成员流水数据
        chatGroupFlowService.addChatGroupFlow(userChatGroup);

    }

    /**
     * 群组删除成员
     *
     * @param userChatGroup
     */
    @Override
    @Transactional
    public void deleteGroupMember(UserChatGroup userChatGroup) {
        //删除群成员数据
        userChatGroup.setDelFlag(CommonConstant.YES);
        saveOrUpdate(userChatGroup);
        //添加成员流水数据
        chatGroupFlowService.addChatGroupFlow(userChatGroup);
    }

    /**
     * 更新用户群组权限
     *
     * @param userChatGroup
     */
    @Override
    @Transactional
    public void updateMemberAuth(UserChatGroup userChatGroup) {
        //更新用户角色
        saveOrUpdate(userChatGroup);
        //添加群组操作流水
        chatGroupOperationFlowService.addMemberAuthOperationFlow(userChatGroup);
    }

    /**
     * 模糊查找用户
     *
     * @param uid
     * @param targetId
     * @param condition
     * @return
     */
    @Override
    public List<FindUserVO> findUser(String uid, String targetId, String condition) {
        UserChatGroup userChatGroup = selectUserByGroupIdAndUid(targetId, uid);
        if (userChatGroup == null || userChatGroup.getDelFlag() == CommonConstant.YES){
            LOG.info("用户未加入该群组, 无法搜索群组成员, groupId:{}, uid:{}", targetId, uid);
            throw new BusinessException(BusinessExceptionEnum.GROUP_MEMBER_NOT_EXIST_EXCEPTION.getFailCode(), BusinessExceptionEnum.GROUP_MEMBER_NOT_EXIST_EXCEPTION.getFailReason());
        }

        return userChatGroupMapper.findUser(targetId, condition);
    }

    @Override
    public void loadGroupUser(JSONObject param, ChannelHandlerContext ctx) {
        String userId = String.valueOf(param.get("userId"));
        if(Constant.onlineUserMap.get(userId) != null) {
            List<UserChatGroupVO> userChatGroupVOS = userChatGroupMapper.selectByUidAndGid(String.valueOf(param.get("userId")), String.valueOf(param.get("chatGroupId")));
            String responseJson = new ResponseJson().success()
                    .setData("type", ChatTypeEnum.LOAD_GROUP_USER)
                    .setData("content", userChatGroupVOS)
                    .toString();
            ctx.channel().writeAndFlush(new TextWebSocketFrame(responseJson));
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                LOG.info("加载用户数据不成功" + e.getMessage());
            }
        }
    }

    /**
     * 查询工会列表
     * @param param
     * @param ctx
     */
    @Override
    public void userGroupList(JSONObject param, ChannelHandlerContext ctx) {
        String userId = String.valueOf(param.get("userId"));
        if(Constant.onlineUserMap.get(userId) != null) {
            List<UserChatVO> userChatGroup = userChatGroupMapper.selectByPrimaryKey(String.valueOf(param.get("userId"))
                    /*CommonConstant.NO*/);
            String responseJson = new ResponseJson().success()
                    .setData("type", ChatTypeEnum.USER_GROUP_LIST)
                    .setData("content", userChatGroup)
                    .toString();
            ctx.channel().writeAndFlush(new TextWebSocketFrame(responseJson));
        }
    }

    public void flushGroupMsg(String groupId){
        List<UserChatGroup> userChatGroup = chatGroupService.getByGroupId(groupId);
        for (UserChatGroup chatGroup : userChatGroup) {
            Map<String, ChannelHandlerContext> onlineUserMap = Constant.onlineUserMap;
            ChannelHandlerContext ctx = onlineUserMap.get(chatGroup.getUserId());
            if (ctx != null){
                JSONObject param = new JSONObject();
                param.put("userId",chatGroup.getUserId());
                param.put("chatGroupId",groupId);
                loadGroupUser(param,ctx);
                userGroupList(param,ctx);
            }
        }
    }

    @Override
    public List<UserChatGroup> selectByUserId(String userId) {
        return userChatGroupMapper.selectByUserId(userId);
    }

    @Override
    public void updateUserMessage(String name, String groupId, String nickname) {
        userChatGroupMapper.updateUserMessage(name ,groupId,nickname);
    }

    /**
     * 构建群主群组信息
     *
     * @param chatGroup
     * @return
     */
    private UserChatGroup buildMasterChatGroup(ChatGroup chatGroup) {
        UserChatGroup userChatGroup = new UserChatGroup();
        userChatGroup.setUserId(chatGroup.getMaster());
        userChatGroup.setChatGroupId(chatGroup.getId());
        userChatGroup.setTop(CommonConstant.YES);
        userChatGroup.setType(GroupRoleEnum.MASTER.value());
        userChatGroup.setDelFlag(CommonConstant.NO);
        userChatGroup.setCreateTime(new Date());
        LOG.info("构建用户群组信息, userChatGroup:{}", JSON.toJSONString(userChatGroup));

        return userChatGroup;
    }
}
