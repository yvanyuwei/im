package com.vm.im.common.dto.admin;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

/**
 * @ClassName: AuthOperationDTO
 * @Description: 权限操作DTO
 * @Author zhangqi
 * @Date 2019年02月27日16时13分
 * @Version 1.0
 */
@Data
public class AuthOperationDTO {

    @NotEmpty(message = "uid不能为空")
    private String uid;

    @NotEmpty(message = "groupId不能为空")
    private String groupId;

    @NotEmpty(message = "timestamp不能为空")
    private String timestamp;

    @Min(value = 0)
    @Max(value = 1)
    private int type;

}
