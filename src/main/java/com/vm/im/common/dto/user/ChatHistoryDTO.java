package com.vm.im.common.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @ClassName: ChatHistoryDTO
 * @Description: 聊天历史请求参数
 * @Author zhangqi
 * @Date 2019年03月26日10时19分
 * @Version 1.0
 */
@Data
@ApiModel
public class ChatHistoryDTO {

    /**
     * from_id
     */
    @ApiModelProperty(required = true, value = "发送消息的id")
    @NotEmpty(message = "fromId不能为空")
    private String fromId;

    /**
     * to_id
     */
    @ApiModelProperty(required = true, value = "接收消息的id")
    @NotEmpty(message = "toId不能为空")
    private String toId;

    /**
     * type 消息类型
     */
    @ApiModelProperty(required = true, value = "消息类型 1: 单聊, 3: 群聊")
    @NotNull(message = "type不能为空")
    private int type;

    /**
     * 查询的条数
     */
    @ApiModelProperty(required = true, value = "加载的条数")
    @NotNull(message = "count不能为空")
    private int count;

    /**
     * 消息时间戳
     */
    @ApiModelProperty(required = true, value = "最早的消息时间戳")
    private Long timestamp;
}
