package com.vm.im.common.dto.admin;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

/**
 * @ClassName: MemberOperationDTO
 * @Description: 成员操作DTO
 * @Author zhangqi
 * @Date 2019年02月27日16时14分
 * @Version 1.0
 */
@Data
public class MemberOperationDTO {

    @NotEmpty(message = "uid不能为空")
    private String uId;

    @NotEmpty(message = "groupId不能为空")
    private String groupId;

    @NotEmpty(message = "timestamp不能为空")
    private Long timestamp;

    @Min(value = 0)
    @Max(value = 1)
    private int type;

}
