package com.vm.im.common.dto.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

/**
 * @ClassName: ReceiveRedPacketDTO
 * @Description: 收红包DTO
 * @Author zhangqi
 * @Date 2019年03月12日15时47分
 * @Version 1.0
 */
@Data
@ApiModel
public class ReceiveRedPacketDTO {

    /**
     * 红包业务id
     */
    @ApiModelProperty(required = true, value = "红包id")
    @NotEmpty(message = "id不能为空")
    private String id;

    /**
     * 红包id
     */
    @ApiModelProperty(required = true, value = "红包id")
    @NotEmpty(message = "redPacketId不能为空")
    private String redPacketId;

    /**
     * 发送人id
     */
    @ApiModelProperty(required = true, value = "发送人id")
    @NotEmpty(message = "fromId不能为空")
    private String fromId;

    /**
     * 接收人id
     */
    @ApiModelProperty(required = true, value = "接收人id")
    @NotEmpty(message = "toId不能为空")
    private String toId;

    /**
     * 类型 1: 个人红包, 3：群红包 5: 工会红包
     */
    @ApiModelProperty(required = true, value = "类型")
    @NotEmpty(message = "type不能为空")
    private Integer type;

    /**
     * 币种
     */
    @ApiModelProperty(required = true, value = "币种")
    @NotEmpty(message = "coinId不能为空")
    private String coinId;

    /**
     * 币种名称
     */
    @ApiModelProperty(required = true, value = "币种名称")
    @NotEmpty(message = "coinName不能为空")
    private String coinName;

    /**
     * 金额
     */
    @ApiModelProperty(required = true, value = "金额")
    private BigDecimal amount;

    /**
     * 状态 -1： 无效的, 0: 失败的, 1成功
     */
    private Integer status;

    @ApiModelProperty(required = true, value = "时间")
    @NotEmpty(message = "createTime不能为空")
    private String createTime;

}
