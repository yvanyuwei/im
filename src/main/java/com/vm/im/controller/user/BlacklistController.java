package com.vm.im.controller.user;


import com.alibaba.fastjson.JSON;
import com.vm.im.entity.user.Blacklist;
import com.vm.im.service.user.BlacklistService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * <p>
 * 黑名单 前端控制器
 * </p>
 *
 * @author zhangqi
 * @since 2019-02-19
 */
@RestController
@RequestMapping("/blacklist")
@Api(value = "黑名单", description = "用户黑名单相关接口")
public class BlacklistController {
    private static final Logger LOG = LoggerFactory.getLogger(BlacklistController.class);

    @Autowired
    private BlacklistService blacklistService;

    @PostMapping("list")
    @ApiOperation(value = "黑名单列表", notes = "获取所有黑名单数据")
    public String blackList() {
        Blacklist blacklist = new Blacklist();
        blacklist.setBlackUserId("sdf");
        blacklist.setId("sdfsdssdfss");
        blacklist.setUserId("长啥扣水电费三等奖#5452*!@撒大声地");
        blacklist.setCreateTime(new Date());
        List<Blacklist> list = new ArrayList<>();
        list.add(blacklist);
        Blacklist sdfsdssdfss = blacklistService.getById("sdfsdssdfss");
        boolean save = blacklistService.updateBatchById(list);
        System.out.println(save);
        System.out.println(sdfsdssdfss.toString());

        return JSON.toJSONString(blacklist);
    }
}

