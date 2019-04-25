package com.vm.im.controller.user;


import com.alibaba.fastjson.JSON;
import com.vm.im.common.annot.UserAuth;
import com.vm.im.common.dto.ResultBean;
import com.vm.im.common.dto.user.ChatHistoryDTO;
import com.vm.im.common.dto.user.FindUserDTO;
import com.vm.im.common.enums.BusinessExceptionEnum;
import com.vm.im.common.enums.ResultCodeEnum;
import com.vm.im.common.exception.BusinessException;
import com.vm.im.common.vo.user.ChatHistoryVO;
import com.vm.im.common.vo.user.FindUserVO;
import com.vm.im.service.common.MessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private MessageService messageService;

    /*@RequestMapping("test")
    public String test(){
        String responseJson = new ResponseJson().success().setData("name",12313).setData("id",21321).toString();
        return responseJson;
    }*/

    /*@RequestMapping(value = {"login","/"}, method = RequestMethod.GET)
    public String toLogin() {
        return "/page/login.html";
    }*/

    /*@RequestMapping(value = "login",method = RequestMethod.POST)
    @ResponseBody
    public ResponseJson login(HttpSession session, @RequestParam String username, @RequestParam String password) {
        return userService.login(username, password, session);
    }*/

    @UserAuth
    @ApiOperation(value = "查找用户", notes = "模糊查找用户")
    @PostMapping("findUser")
    public String findUser(@RequestBody @Valid FindUserDTO findUserDTO, @RequestHeader(value = USERID) String uid) {
        LOG.info("收到查找用户请求, param:{}", JSON.toJSONString(findUserDTO));
        List<FindUserVO> userList = messageService.findUserList(findUserDTO, uid);
        return JSON.toJSONString(new ResultBean(ResultCodeEnum.SUCCESS.getCode(), JSON.toJSONString(userList), null));
    }

    @UserAuth
    @ApiOperation(value = "聊天历史", notes = "分页查找聊天历史")
    @PostMapping("chatHistory")
    public String chatHistory(@RequestBody @Valid @ApiParam(name = "查找聊天历史对象", value = "传入json格式") ChatHistoryDTO chatHistoryDTO, @RequestHeader(value = USERID) String uid) {
        LOG.info("收到获取聊天历史请求, param:{}", JSON.toJSONString(chatHistoryDTO));
        if (!chatHistoryDTO.getFromId().equals(uid)){
            LOG.info("请求头的UID与fromId不符, userId:{}, fromId:{}", uid, chatHistoryDTO.getFromId());
            throw new BusinessException(BusinessExceptionEnum.USER_INFO_EXCEPTION.getFailCode(), BusinessExceptionEnum.USER_INFO_EXCEPTION.getFailReason());
        }

        List<ChatHistoryVO> result = messageService.chatHistory(chatHistoryDTO);
        return JSON.toJSONString(new ResultBean(ResultCodeEnum.SUCCESS.getCode(), JSON.toJSONString(result), null));
    }
}
