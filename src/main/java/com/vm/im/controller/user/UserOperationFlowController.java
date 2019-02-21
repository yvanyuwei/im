package com.vm.im.controller.user;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户操作流水记录表 前端控制器
 * </p>
 *
 * @author zhangqi
 * @since 2019-02-19
 */
@RestController
@RequestMapping("/userOperationFlow")
public class UserOperationFlowController {
    private static final Logger LOG = LoggerFactory.getLogger(UserOperationFlowController.class);
}

