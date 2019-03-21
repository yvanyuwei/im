package com.vm.im.service.user.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vm.im.common.constant.CommonConstant;
import com.vm.im.common.dto.admin.CreateUserDTO;
import com.vm.im.common.dto.user.FindUserDTO;
import com.vm.im.common.enums.FindUserTypeEnum;
import com.vm.im.common.util.RedisUtil;
import com.vm.im.common.util.ResponseJson;
import com.vm.im.common.vo.user.FindUserVO;
import com.vm.im.dao.user.UserMapper;
import com.vm.im.entity.user.User;
import com.vm.im.entity.user.UserChatGroup;
import com.vm.im.entity.user.UserCurrentChat;
import com.vm.im.entity.user.UserFriend;
import com.vm.im.service.user.UserChatGroupService;
import com.vm.im.service.user.UserCurrentChatService;
import com.vm.im.service.user.UserFriendService;
import com.vm.im.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.vm.im.common.constant.CommonConstant.PLACEHOLDER;


/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author zhangqi
 * @since 2019-02-19
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserChatGroupService userChatGroupService;

    @Autowired
    private UserCurrentChatService userCurrentChatService;

    @Autowired
    private UserFriendService userFriendService;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public ResponseJson getByUserId(String userId) {
        //saveUserInfo();
        User user = userMapper.selectById(userId);
        return new ResponseJson().success().setData("userInfo", user);
    }

    /**
     * 根据查找目标体查找用户
     *
     * @param findUserDTO
     * @param uid
     * @return
     */
    @Override
    public List<FindUserVO> findUserList(FindUserDTO findUserDTO, String uid) {
        List<FindUserVO> userList = new ArrayList<>();
        findUserDTO.setCondition(PLACEHOLDER + findUserDTO.getCondition() + PLACEHOLDER);
        switch (FindUserTypeEnum.valueOf(findUserDTO.getType())) {
            case CHAT_GROUP:
                userList = userChatGroupService.findUser(uid, findUserDTO.getTargetId(), findUserDTO.getCondition());
                break;
            case CURRENT_CHAT:
                userList = userCurrentChatService.findUser(uid, findUserDTO.getCondition());
                break;
            case FRIEND:
                userList = userFriendService.findUser(uid, findUserDTO.getCondition());
                break;
            case ALL:
                userList = findUser(findUserDTO.getCondition());
                break;
            default:
                LOG.info("未知的搜索目标");
                break;
        }
        return userList;
    }

    /**
     * 更新保存用户信息
     */
    //@Scheduled(cron = "0/10 * *  * * ? ")
    @Async
    public void saveUserInfo(User user) {
        saveOrUpdate(user);
        List<UserFriend> userFriends = userFriendService.selectByFriendId(user.getId(), CommonConstant.NO);
        for (UserFriend userFriend : userFriends) {
            if (!user.getName().equals(userFriend.getNickname())) {
                userFriendService.updateUserMessage(user.getName(), userFriend.getFriendId(), userFriend.getNickname());
            }
        }
        List<UserChatGroup> userChatGroups = userChatGroupService.selectByUserId(user.getId());
        for (UserChatGroup userChatGroup : userChatGroups) {
            if (!user.getName().equals(userChatGroup.getNickname())) {
                userChatGroupService.updateUserMessage(user.getName(), userChatGroup.getChatGroupId(),
                        userChatGroup.getNickname());
            }
        }
        //}
        List<UserCurrentChat> userCurrentChats = userCurrentChatService.selectByFriendId(user.getId());
        for (UserCurrentChat userCurrentChat : userCurrentChats) {
            if (!user.getName().equals(userCurrentChat.getNickname())) {
                userCurrentChatService.updateUserMessage(user.getName(), userCurrentChat.getFriendId(),
                        userCurrentChat.getNickname());
            }

        }
    }

    @Override
    public void createUser(CreateUserDTO createUserDTO) {
        User user = buildUserMsg(createUserDTO);
        save(user);
        redisUtil.hset(CommonConstant.REDIS_USER_INFO, user.getId(), JSON.toJSONString(user));
    }

    private User buildUserMsg(CreateUserDTO createUserDTO) {
        User user = new User();
        user.setId(String.valueOf(createUserDTO.getId()));
        user.setAvatar(createUserDTO.getAvatar());
        user.setName(createUserDTO.getName());
        if (createUserDTO.getMobile() != null) {
            user.setMobile(createUserDTO.getMobile());
        }
        if (createUserDTO.getEmail() != null) {
            user.setEmail(createUserDTO.getEmail());
        }
        user.setCreateTime(new Date(Long.valueOf(createUserDTO.getCreateTime())));
        user.setDelFlag(CommonConstant.NO);
        LOG.info("构建用户信息, userChatGroup:{}", JSON.toJSONString(user));
        return user;
    }

    /**
     * 模糊查找用户
     *
     * @param condition
     * @return
     */
    @Override
    public List<FindUserVO> findUser(String condition) {
        return userMapper.findUser(condition);
    }
}