package com.vm.im.common.dto.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @ClassName: UserInfoDTO
 * @Description: 更新用户信息DTO
 * @Author zhangqi
 * @Date 2019年03月14日17时31分
 * @Version 1.0
 */
@ApiModel
@Data
public class UserInfoDTO {

    /**
     * 用户id
     */
    @ApiModelProperty(required = true, value = "用户id")
    @NotEmpty(message = "userId不能为空")
    private String userId;

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
