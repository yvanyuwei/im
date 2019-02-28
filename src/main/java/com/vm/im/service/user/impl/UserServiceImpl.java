package com.vm.im.service.user.impl;

import com.vm.im.common.util.ResponseJson;
import com.vm.im.entity.user.User;
import com.vm.im.dao.user.UserMapper;
import com.vm.im.netty.Constant;
import com.vm.im.service.user.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

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

    @Override
    public ResponseJson login(String username, String password, HttpSession session) {
        User user = userMapper.selectByUserName(username);
        if(user == null ){
            return new ResponseJson().error("用户名不存在");
        }
        if(!user.getPassword().equals(password)){
            return new ResponseJson().error("密码错误");
        }
        session.setAttribute(Constant.USER_TOKEN,user.getId());
        LOG.info("================"+new ResponseJson().success());
        return new ResponseJson().success();
    }

    @Override
    public ResponseJson getByUserId(String userId) {
        User user = userMapper.selectById(userId);
        return new ResponseJson().success().setData("userInfo",user);
    }


}
