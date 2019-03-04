package com.vm.im.controller.user;


import com.alibaba.fastjson.JSON;
import com.vm.im.common.annot.UserAuth;
import com.vm.im.common.dto.user.FindUserDTO;
import com.vm.im.common.util.ResponseJson;
import com.vm.im.common.vo.user.FindUserVO;
import com.vm.im.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

import static com.vm.im.common.constant.CommonConstant.USERID;


/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author zhangqi
 * @since 2019-02-19
 */
@RestController
@RequestMapping("/user")
@Api(value = "用户相关", description = "用户相关接口")
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

    @UserAuth
    @ApiOperation(value = "查找用户", notes = "模糊查找用户")
    @PostMapping("findUser")
    public String findUser(@RequestBody @Valid FindUserDTO findUserDTO, @RequestHeader(value=USERID) String uid){
        List<FindUserVO> userList = userService.findUserList(findUserDTO, uid);
        return JSON.toJSONString(userList);
    }
}
