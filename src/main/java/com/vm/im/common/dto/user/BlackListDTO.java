package com.vm.im.common.dto.user;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @ClassName: BlackListDTO
 * @Description: BlackListDTO
 * @Author zhangqi
 * @Date 2019年02月22日10时35分
 * @Version 1.0
 */
@Data
public class BlackListDTO {
    @NotEmpty(message = "uid不能为空")
    private String uid;

}
