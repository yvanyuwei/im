package com.vm.im.service.user.impl;

import com.vm.im.common.dto.user.FindUserDTO;
import com.vm.im.common.enums.FindUserTypeEnum;
import com.vm.im.common.util.ResponseJson;
import com.vm.im.common.vo.user.FindUserVO;
import com.vm.im.entity.user.User;
import com.vm.im.dao.user.UserMapper;
import com.vm.im.netty.Constant;
import com.vm.im.service.user.UserChatGroupService;
import com.vm.im.service.user.UserCurrentChatService;
import com.vm.im.service.user.UserFriendService;
import com.vm.im.service.user.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
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


    @Override
    public ResponseJson login(String username, String password, HttpSession session) {
        User user = userMapper.selectByUserName(username);
        if (user == null) {
            return new ResponseJson().error("用户名不存在");
        }
        if (!user.getPassword().equals(password)) {
            return new ResponseJson().error("密码错误");
        }
        session.setAttribute(Constant.USER_TOKEN, user.getId());
        LOG.info("================" + new ResponseJson().success());
        return new ResponseJson().success();
    }

    @Override
    public ResponseJson getByUserId(String userId) {
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