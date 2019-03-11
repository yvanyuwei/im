package com.vm.im.controller.admin;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.vm.im.common.annot.AdminAuth;
import com.vm.im.common.constant.CommonConstant;
import com.vm.im.common.dto.ResultBean;
import com.vm.im.common.dto.admin.AuthOperationDTO;
import com.vm.im.common.dto.admin.CreateUserDTO;
import com.vm.im.common.dto.admin.MemberOperationDTO;
import com.vm.im.common.dto.admin.UnionOperationDTO;
import com.vm.im.common.enums.AdminRoleEnum;
import com.vm.im.common.enums.BusinessExceptionEnum;
import com.vm.im.common.enums.ResultCodeEnum;
import com.vm.im.common.exception.BusinessException;
import com.vm.im.entity.user.User;
import com.vm.im.entity.user.UserChatGroup;
import com.vm.im.netty.Constant;
import com.vm.im.service.group.ChatGroupService;
import com.vm.im.service.user.UserChatGroupService;
import com.vm.im.service.user.UserService;
import io.netty.channel.ChannelHandlerContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 内部通信接口 前端控制器
 * </p>
 *
 * @author zhangqi
 * @since 2019-02-19
 */
@RestController
@RequestMapping("/admin")
@Api(value = "内部通讯相关", description = "内部用户信息通讯")
public class AdminController {
    private static final Logger LOG = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private ChatGroupService chatGroupService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserChatGroupService userChatGroupService;


    @AdminAuth(roles = {AdminRoleEnum.ADMIN})
    @PostMapping("unionOperation")
    @ApiOperation(value = "工会操作", notes = "创建和解散工会使用接口")
    public String unionOperation(@RequestBody @Valid UnionOperationDTO unionOperationDTO) {
        if (CommonConstant.YES.equals(unionOperationDTO.getType())) {
            LOG.info("收到创建工会群申请, groupId:{}", unionOperationDTO.getGroupId());
            chatGroupService.createUnionGroup(unionOperationDTO);
            chatGroupService.loadGroupInfo();
        }

        if (CommonConstant.NO.equals(unionOperationDTO.getType())) {
            LOG.info("收到解散工会群申请, groupId:{}", unionOperationDTO.getGroupId());
            chatGroupService.deleteUnionGroup(unionOperationDTO);
            chatGroupService.loadGroupInfo();
        }

        return JSON.toJSONString(new ResultBean(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.name(), null));
    }

    @AdminAuth(roles = {AdminRoleEnum.ADMIN})
    @PostMapping("memberOperation")
    @ApiOperation(value = "人员增删操作", notes = "工会添加删除成员使用接口")
    public String memberOperation(@RequestBody @Valid MemberOperationDTO memberOperationDTO) {
        if (CommonConstant.YES.equals(memberOperationDTO.getType())) {
            LOG.info("收到群人员添加操作, groupId:{}", memberOperationDTO.getGroupId());
            chatGroupService.addUnionMember(memberOperationDTO);
            userChatGroupService.flushGroupMsg(memberOperationDTO.getGroupId());
        }
        if (CommonConstant.NO.equals(memberOperationDTO.getType())) {
            LOG.info("收到群人员删除操作, groupId:{}", memberOperationDTO.getGroupId());
            chatGroupService.deleteUnionMember(memberOperationDTO);
            userChatGroupService.flushGroupMsg(memberOperationDTO.getGroupId());
        }
        return JSON.toJSONString(new ResultBean(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.name(), null));
    }

    @AdminAuth(roles = {AdminRoleEnum.ADMIN})
    @PostMapping("authOperation")
    @ApiOperation(value = "人员权限操作", notes = "工会管理员增删使用接口")
    public String authOperation(@RequestBody @Valid AuthOperationDTO authOperationDTO) {
        if (CommonConstant.YES.equals(authOperationDTO.getType())) {
            LOG.info("收到群人员管理员权限添加操作, groupId:{}", authOperationDTO.getGroupId());
            chatGroupService.addUnionMemberAuth(authOperationDTO);
            userChatGroupService.flushGroupMsg(authOperationDTO.getGroupId());
        }

        if (CommonConstant.NO.equals(authOperationDTO.getType())) {
            LOG.info("收到群人员管理员权限取消操作, groupId:{}", authOperationDTO.getGroupId());
            chatGroupService.deleteUnionMemberAuth(authOperationDTO);
            userChatGroupService.flushGroupMsg(authOperationDTO.getGroupId());
        }

        return JSON.toJSONString(new ResultBean(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.name(), null));
    }

    @AdminAuth(roles = {AdminRoleEnum.ADMIN})
    @PostMapping("createUser")
    @ApiOperation(value = "创建用户", notes = "创建用户使用接口")
    public String createUser(@RequestBody @Valid CreateUserDTO createUserDTO) {
        User userMsg = userService.getById(createUserDTO.getId());
        if (userMsg == null){
            userService.createUser(createUserDTO);
        }else{
            throw new BusinessException(BusinessExceptionEnum.USER_EXIST_EXCEPTION.getFailCode(), BusinessExceptionEnum.USER_EXIST_EXCEPTION.getFailReason());
        }
        return JSON.toJSONString(new ResultBean(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.name(), null));
    }
}

