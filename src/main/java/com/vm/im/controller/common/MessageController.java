package com.vm.im.controller.common;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 信息表 前端控制器
 * </p>
 *
 * @author zhangqi
 * @since 2019-02-20
 */
@RestController
@RequestMapping("/message")
public class MessageController {
    private static final Logger LOG = LoggerFactory.getLogger(MessageController.class);
}

