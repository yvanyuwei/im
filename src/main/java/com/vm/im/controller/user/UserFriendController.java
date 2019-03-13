package com.vm.im.controller.user;


import com.vm.im.service.user.UserFriendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户好友 前端控制器
 * </p>
 *
 * @author zhangqi
 * @since 2019-02-19
 */
@RestController
@RequestMapping("/userFriend")
public class UserFriendController {
    private static final Logger LOG = LoggerFactory.getLogger(UserFriendController.class);

    @Autowired
    private UserFriendService userFriendService;

    /*@PostMapping("userFriends")
    @ApiOperation(value = "用户好友列表", notes = "查询当前用户好友列表接口")
    public String userFriends(@RequestBody @Valid UserFriendListDTO userFriendListDTO){
        LOG.info("收到查询用户好友列表操作, userId:{}", userFriendListDTO.getUid());
        List<UserFriend> userFriends = userFriendService.selectUserFriend(userFriendListDTO);
        return JSON.toJSONString(new ResultBean(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.name(),
                JSON.toJSONString(userFriends)));
    }*/
}

