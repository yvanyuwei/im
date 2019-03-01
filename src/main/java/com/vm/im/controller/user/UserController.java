package com.vm.im.controller.user;


import com.vm.im.common.util.ResponseJson;
import com.vm.im.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author zhangqi
 * @since 2019-02-19
 */
@Controller
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping("test")
    public String test(){
        String responseJson = new ResponseJson().success().setData("name",12313).setData("id",21321).toString();
        return responseJson;
    }

    @RequestMapping(value = {"login","/"}, method = RequestMethod.GET)
    public String toLogin() {
        return "/page/login.html";
    }

    @RequestMapping(value = "login",method = RequestMethod.POST)
    @ResponseBody
    public ResponseJson login(HttpSession session, @RequestParam String username, @RequestParam String password) {
        return userService.login(username, password, session);
    }
}

