package com.vm.im.common.dto.admin;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @ClassName: CreateUserDTO
 * @Description: 创建用户DTO
 * @Author zhangqi
 * @Date 2019年02月27日16时13分
 * @Version 1.0
 */
@Data
public class CreateUserDTO {

    @NotEmpty(message = "id不能为空")
    private String id;

    @NotEmpty(message = "avatar不能为空")
    private String avatar;

    @NotEmpty(message = "name不能为空")
    private String name;

    private String mobile;

    private String email;

    @NotEmpty(message = "createTime不能为空")
    private String createTime;

}
