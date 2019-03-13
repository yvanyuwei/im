package com.vm.im.controller.chat;

import com.alibaba.fastjson.JSON;
import com.vm.im.common.util.ResponseJson;
import com.vm.im.common.vo.user.UserToken;
import com.vm.im.controller.aop.NeedUserAuth;
import com.vm.im.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chatroom")
public class ChatController {

    @Autowired
    private UserService userService;

    @Autowired
    private NeedUserAuth needUserAuth;

    /*@RequestMapping("test")
    public String test(){
        return "/page/login.html";
    }*/



    @RequestMapping(method = RequestMethod.GET)
    public String toChatroom() {
        return "/page/chatroom.html";
    }

    /**
     * 调用此接口获取用户信息
     * @param userId
     * @return
     */
    /*@RequestMapping(value = "/get_userinfo", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJson getUserInfo() {
        String msg = needUserAuth.checkToken();
        UserToken userToken = JSON.parseObject(msg, UserToken.class);
        return userService.getByUserId(String.valueOf(userToken.getId()));
    }*/
}
