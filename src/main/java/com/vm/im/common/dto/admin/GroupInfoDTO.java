package com.vm.im.common.dto.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @ClassName: GroupInfoDTO
 * @Description: 更新群组信息DTO
 * @Author zhangqi
 * @Date 2019年03月14日17时38分
 * @Version 1.0
 */
@ApiModel
@Data
public class GroupInfoDTO {

    /**
     * 群id
     */
    @ApiModelProperty(required = true, value = "群id")
    @NotEmpty(message = "GroupId不能为空")
    private String GroupId;

    /**
     * 旧名字
     */
    @ApiModelProperty(required = true, value = "旧名字")
    @NotEmpty(message = "oldName不能为空")
    private String oldName;

    /**
     * 新名字
     */
    @ApiModelProperty(required = true, value = "新名字")
    @NotEmpty(message = "newName不能为空")
    private String newName;

    /**
     * 头像
     */
    @ApiModelProperty(required = true, value = "头像")
    @NotEmpty(message = "avatar不能为空")
    private String avatar;
}
