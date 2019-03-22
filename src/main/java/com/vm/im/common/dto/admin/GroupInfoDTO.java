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
    @NotEmpty(message = "groupId不能为空")
    private String groupId;

    /**
     * 旧名字
     */
    @ApiModelProperty(value = "旧名字")
    private String oldName;

    /**
     * 新名字
     */
    @ApiModelProperty(value = "新名字")
    private String newName;

    /**
     * 头像
     */
    @ApiModelProperty(value = "头像")
    private String avatar;
}
