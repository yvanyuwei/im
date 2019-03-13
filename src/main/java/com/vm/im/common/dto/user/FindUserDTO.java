package com.vm.im.common.dto.user;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

/**
 * @ClassName: FindUserDTO
 * @Description: 查找用户参数
 * @Author zhangqi
 * @Date 2019年03月02日15时07分
 * @Version 1.0
 */
@Data
public class FindUserDTO {

    private String targetId;

    @Min(value = 1)
    @Max(value = 4)
    private int type;

    @NotEmpty(message = "查询条件不能为空")
    private String condition;

}
