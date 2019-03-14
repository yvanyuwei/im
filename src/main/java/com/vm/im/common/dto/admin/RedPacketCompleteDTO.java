package com.vm.im.common.dto.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

/**
 * @ClassName: RedPacketCompleteDTO
 * @Description: 红包抢完接口参数
 * @Author zhangqi
 * @Date 2019年03月13日14时48分
 * @Version 1.0
 */
@ApiModel
@Data
public class RedPacketCompleteDTO {

    /**
     * 红包id
     */
    @ApiModelProperty(required = true, value = "红包id")
    @NotEmpty(message = "redPacketId不能为空")
    private String redPacketId;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    @Min(value = 1)
    @Max(value = 9)
    private Integer status;
}
