package com.vm.im.service.user.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;
import com.vm.im.common.constant.CommonConstant;
import com.vm.im.common.util.ResponseJson;
import com.vm.im.controller.aop.NeedUserAuth;
import com.vm.im.entity.user.User;
import com.vm.im.dao.user.UserMapper;
import com.vm.im.entity.user.UserToken;
import com.vm.im.netty.Constant;
import com.vm.im.service.user.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;


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
    private NeedUserAuth needUserAuth;

    @Override
    public ResponseJson login(String username, String password, HttpSession session) {
        User user = userMapper.selectByUserName(username);
        if(user == null ){
            return new ResponseJson().error("用户名不存在");
        }
        if(!user.getPassword().equals(password)){
            return new ResponseJson().error("密码错误");
        }
        //session.setAttribute(Constant.USER_TOKEN,user.getId());
        LOG.info("================"+new ResponseJson().success());
        return new ResponseJson().success();
    }

    @Override
    public ResponseJson getByUserId(String userId) {
        User user = userMapper.selectById(userId);
        return new ResponseJson().success().setData("userInfo",user);
    }

    /**
     * 保存用户信息
     */
    public void saveUserInfo() {
        String userMsg = needUserAuth.checkToken();
        if (userMsg != null){
            UserToken userToken = JSON.parseObject(userMsg, UserToken.class);
            User user = buildUserMessage(userToken);
            saveOrUpdate(user);
        }
    }

    private User buildUserMessage(UserToken userToken){
        User user = new User();
        user.setId(String.valueOf(userToken.getId()));
        user.setAvatar(userToken.getImage());
        user.setName(userToken.getUsername());
        user.setMobile(userToken.getPhonenum());
        user.setEmail(userToken.getMail());
        user.setPassword(userToken.getPassword());
        user.setDelFlag(CommonConstant.NO);
        user.setCreateTime(new Date(userToken.getCreatetime()));
        LOG.info("构建用户信息, userChatGroup:{}",JSON.toJSONString(user));
        return user;
    }
}
