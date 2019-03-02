package com.vm.im.controller.admin;


import com.alibaba.fastjson.JSON;
import com.vm.im.common.annot.AdminAuth;
import com.vm.im.common.constant.CommonConstant;
import com.vm.im.common.dto.ResultBean;
import com.vm.im.common.dto.admin.AuthOperationDTO;
import com.vm.im.common.dto.admin.MemberOperationDTO;
import com.vm.im.common.dto.admin.UnionOperationDTO;
import com.vm.im.common.enums.AdminRoleEnum;
import com.vm.im.common.enums.ResultCodeEnum;
import com.vm.im.service.group.ChatGroupService;
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


    @AdminAuth(roles = {AdminRoleEnum.ADMIN})
    @PostMapping("unionOperation")
    @ApiOperation(value = "工会操作", notes = "创建和解散工会使用接口")
    public String unionOperation(@RequestBody @Valid UnionOperationDTO unionOperationDTO) {
        if (CommonConstant.YES.equals(unionOperationDTO.getType())) {
            LOG.info("收到创建工会群申请, groupId:{}", unionOperationDTO.getGroupId());
            chatGroupService.createUnionGroup(unionOperationDTO);
        }

        if (CommonConstant.NO.equals(unionOperationDTO.getType())) {
            LOG.info("收到解散工会群申请, groupId:{}", unionOperationDTO.getGroupId());
            chatGroupService.deleteUnionGroup(unionOperationDTO);
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
        }

        if (CommonConstant.NO.equals(memberOperationDTO.getType())) {
            LOG.info("收到群人员删除操作, groupId:{}", memberOperationDTO.getGroupId());
            chatGroupService.deleteUnionMember(memberOperationDTO);
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
        }

        if (CommonConstant.NO.equals(authOperationDTO.getType())) {
            LOG.info("收到群人员管理员权限取消操作, groupId:{}", authOperationDTO.getGroupId());
            chatGroupService.deleteUnionMemberAuth(authOperationDTO);
        }

        return JSON.toJSONString(new ResultBean(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.name(), null));
    }
}

