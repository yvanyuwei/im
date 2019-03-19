package com.vm.im.controller.admin;


import com.alibaba.fastjson.JSON;
import com.vm.im.common.annot.AdminAuth;
import com.vm.im.common.constant.CommonConstant;
import com.vm.im.common.dto.ResultBean;
import com.vm.im.common.dto.admin.*;
import com.vm.im.common.enums.*;
import com.vm.im.common.exception.BusinessException;
import com.vm.im.entity.common.RedPacket;
import com.vm.im.entity.common.RedPacketDetial;
import com.vm.im.entity.user.User;
import com.vm.im.service.Redis.RedisService;
import com.vm.im.service.admin.AdminService;
import com.vm.im.service.group.ChatGroupService;
import com.vm.im.service.user.UserChatGroupService;
import com.vm.im.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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

    @Autowired
    private AdminService adminService;

    @Autowired
    private RedisService redisService;

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

        return JSON.toJSONString(new ResultBean(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.name(), null));
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

        return JSON.toJSONString(new ResultBean(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.name(), null));
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

        return JSON.toJSONString(new ResultBean(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.name(), null));
    }

    @AdminAuth(roles = {AdminRoleEnum.ADMIN})
    @PostMapping("createUser")
    @ApiOperation(value = "创建用户", notes = "创建用户使用接口")
    public String createUser(@RequestBody @Valid CreateUserDTO createUserDTO) {
        LOG.info("收到创建用户请求, param:{}", JSON.toJSONString(createUserDTO));

        User userMsg = userService.getById(createUserDTO.getId());
        if (userMsg == null) {
            userService.createUser(createUserDTO);
        } else {
            throw new BusinessException(BusinessExceptionEnum.USER_EXIST_EXCEPTION.getFailCode(), BusinessExceptionEnum.USER_EXIST_EXCEPTION.getFailReason());
        }

        return JSON.toJSONString(new ResultBean(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.name(), null));
    }

    @AdminAuth(roles = {AdminRoleEnum.ADMIN})
    @PostMapping("giveRedPacket")
    @ApiOperation(value = "发红包", notes = "用户发送红包接口")
    public String giveRedPacket(@RequestBody @Valid @ApiParam(name = "红包对象", value = "传入json格式") GiveRedPacketDTO giveRedPacketDTO) {
        User fromUser = redisService.getRedisUserById(giveRedPacketDTO.getFromId());
        if (fromUser == null || fromUser.getDelFlag() == CommonConstant.YES){
            LOG.info("用户不存在,或者状态为不可用, fromUserId:{}", giveRedPacketDTO.getFromId());
            throw new BusinessException(BusinessExceptionEnum.USER_NOT_EXIST_EXCEPTION.getFailCode(), BusinessExceptionEnum.USER_NOT_EXIST_EXCEPTION.getFailReason());
        }

        RedPacket redPacket = adminService.checkRedPacket(giveRedPacketDTO.getId());
        if (redPacket != null){
            LOG.info("红包已存在,  redPacketId:{}", giveRedPacketDTO.getId());
            throw new BusinessException(BusinessExceptionEnum.RED_PACKET_EXIST_EXCEPTION.getFailCode(), BusinessExceptionEnum.RED_PACKET_EXIST_EXCEPTION.getFailReason());
        }

        if (giveRedPacketDTO.getType() == RedPacketTypeEnum.USER.value()) {
            LOG.info("收到发送个人红包请求, param:{}", JSON.toJSONString(giveRedPacketDTO));
            adminService.giveUserRedPacket(fromUser, giveRedPacketDTO);
        } else {
            LOG.info("收到发送群红包请求, param:{}", JSON.toJSONString(giveRedPacketDTO));
            adminService.giveGroupRedPacket(fromUser, giveRedPacketDTO);
        }

        return JSON.toJSONString(new ResultBean(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.name(), null));
    }

    @AdminAuth(roles = {AdminRoleEnum.ADMIN})
    @PostMapping("receiveRedPacket")
    @ApiOperation(value = "收红包", notes = "用户接收红包接口")
    public String receiveRedPacket(@RequestBody @Valid @ApiParam(name = "收红包对象", value = "传入json格式") ReceiveRedPacketDTO receiveRedPacketDTO) {
        User fromUser = redisService.getRedisUserById(receiveRedPacketDTO.getFromId());
        if (fromUser == null || fromUser.getDelFlag() == CommonConstant.YES){
            LOG.info("用户不存在,或者状态为不可用, fromUserId:{}", receiveRedPacketDTO.getFromId());
            throw new BusinessException(BusinessExceptionEnum.USER_NOT_EXIST_EXCEPTION.getFailCode(), BusinessExceptionEnum.USER_NOT_EXIST_EXCEPTION.getFailReason());
        }

        RedPacket redPacket = adminService.checkRedPacket(receiveRedPacketDTO.getRedPacketId());
        if (redPacket == null || redPacket.getStatus() != RedPacketStatusEnum.SUCCESS.value()){
            LOG.info("红包不存在, 或状态为不可用,  redPacketId:{}", receiveRedPacketDTO.getRedPacketId());
            throw new BusinessException(BusinessExceptionEnum.RED_PACKET_NOT_FOUND_EXCEPTION.getFailCode(), BusinessExceptionEnum.RED_PACKET_NOT_FOUND_EXCEPTION.getFailReason());
        }

        RedPacketDetial redPacketDetial = adminService.checkRedPacketDetial(receiveRedPacketDTO.getBusinessId());
        if (redPacketDetial != null){
            LOG.info("该条收红包消息已存在, BusinessId:{}", receiveRedPacketDTO.getBusinessId());
            throw new BusinessException(BusinessExceptionEnum.RED_PACKET_STATUS_EXCEPTION.getFailCode(), BusinessExceptionEnum.RED_PACKET_STATUS_EXCEPTION.getFailReason());
        }

        if (receiveRedPacketDTO.getType() == RedPacketTypeEnum.USER.value()) {
            LOG.info("收到接收个人红包请求, param:{}", JSON.toJSONString(receiveRedPacketDTO));
            adminService.receiveUserRedPacket(fromUser, redPacket, receiveRedPacketDTO);
        } else {
            LOG.info("收到接收群红包请求, param:{}", JSON.toJSONString(receiveRedPacketDTO));
            adminService.receiveGroupRedPacket(fromUser, redPacket, receiveRedPacketDTO);
        }

        return JSON.toJSONString(new ResultBean(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.name(), null));
    }

    @AdminAuth(roles = {AdminRoleEnum.ADMIN})
    @PostMapping("redPacketComplete")
    @ApiOperation(value = "红包抢完", notes = "修改红包状态为已完成接口")
    public String redPacketComplete(@RequestBody @Valid @ApiParam(name = "红包id", value = "传入json格式") RedPacketCompleteDTO redPacketCompleteDTO) {
        LOG.info("收到修改红包状态请求, param:{}", JSON.toJSONString(redPacketCompleteDTO));
        RedPacket redPacket = adminService.checkRedPacket(redPacketCompleteDTO.getRedPacketId());
        if (redPacket == null || redPacket.getStatus() != RedPacketStatusEnum.SUCCESS.value()){
            LOG.info("红包不存在, 或状态为终态,  redPacketId:{}", redPacketCompleteDTO.getRedPacketId());
            throw new BusinessException(BusinessExceptionEnum.RED_PACKET_NOT_FOUND_EXCEPTION.getFailCode(), BusinessExceptionEnum.RED_PACKET_NOT_FOUND_EXCEPTION.getFailReason());
        }
        if (redPacketCompleteDTO.getStatus() != RedPacketStatusEnum.COMOKETE.value()){
            LOG.info("修改状态不支持, redPacketCompleteDTO", JSON.toJSONString(redPacketCompleteDTO));
            throw new BusinessException(BusinessExceptionEnum.RED_PACKET_STATUS_EXCEPTION.getFailCode(), BusinessExceptionEnum.RED_PACKET_STATUS_EXCEPTION.getFailReason());
        }

        redPacket.setStatus(RedPacketStatusEnum.COMOKETE.value());
        adminService.updateRedPacket(redPacket);

        return JSON.toJSONString(new ResultBean(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.name(), null));
    }

    @AdminAuth(roles = {AdminRoleEnum.ADMIN})
    @PostMapping("updateUserInfo")
    @ApiOperation(value = "更新用户信息", notes = "更新用户信息接口")
    public String updateUserInfo(@RequestBody @Valid @ApiParam(name = "用户信息", value = "传入json格式") UserInfoDTO userInfoDTO) {

        return JSON.toJSONString(new ResultBean(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.name(), null));
    }

    @AdminAuth(roles = {AdminRoleEnum.ADMIN})
    @PostMapping("updateGroupInfo")
    @ApiOperation(value = "更新群组信息", notes = "更新群组信息接口")
    public String updateGroupInfo(@RequestBody @Valid @ApiParam(name = "群组信息", value = "传入json格式") GroupInfoDTO groupInfoDTO) {

        return JSON.toJSONString(new ResultBean(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.name(), null));
    }
}

