package com.vm.im.controller.chat;

import com.vm.im.common.util.ResponseJson;
import com.vm.im.netty.Constant;
import com.vm.im.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/chatroom")
public class ChatController {

    @Autowired
    private UserService userService;

    @RequestMapping("test")
    public String test(){
        return "/page/login.html";
    }


    /**
     * 描述：登录成功后，调用此接口进行页面跳转
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public String toChatroom() {
        return "/page/chatroom.html";
    }

    /**
     * 描述：登录成功跳转页面后，调用此接口获取用户信息
     * @param userId
     * @return
     */
    @RequestMapping(value = "/get_userinfo", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJson getUserInfo(HttpSession session) {
        Object userId = session.getAttribute(Constant.USER_TOKEN);
        return userService.getByUserId((String)userId);
    }
}
