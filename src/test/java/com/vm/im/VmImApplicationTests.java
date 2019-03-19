/*

package com.vm.im;

import com.vm.im.common.dto.admin.CreateUserDTO;
import com.vm.im.common.dto.user.UserCurrentDTO;
import com.vm.im.common.util.RedisUtil;
import com.vm.im.common.util.StringUtil;
import com.vm.im.common.vo.user.FindCurrentVO;
import com.vm.im.dao.group.ChatGroupMapper;
import com.vm.im.dao.user.UserChatGroupMapper;
import com.vm.im.dao.user.UserCurrentChatMapper;
import com.vm.im.dao.user.UserFriendMapper;
import com.vm.im.entity.group.ChatGroup;
import com.vm.im.entity.user.UserCurrentChat;
import com.vm.im.entity.user.UserFriend;
import com.vm.im.service.Redis.RedisService;
import com.vm.im.service.group.ChatGroupService;
import com.vm.im.service.user.UserChatGroupService;
import com.vm.im.service.user.UserCurrentChatService;
import com.vm.im.service.user.UserFriendService;
import com.vm.im.service.user.UserService;
import com.vm.im.service.user.impl.UserCurrentChatServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class VmImApplicationTests {

    @Autowired
    UserChatGroupService userChatGroupService;

    @Autowired
    UserFriendService userFriendService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserChatGroupMapper userChatGroupMapper;

    @Autowired
    private UserFriendMapper userFriendMapper;
    @Autowired
    private ChatGroupService chatGroupService;

    @Autowired
    private ChatGroupMapper chatGroupMapper;
    @Autowired
    private UserCurrentChatMapper userCurrentChatMapper;

    @Autowired
    private RedisService redisService;
    @Autowired
    private UserCurrentChatServiceImpl userCurrentChatService;

    @Autowired
    private RedisUtil redisUtil;

    @Test
    public void contextLoads() throws InterruptedException {
        CreateUserDTO createUserDTO = new CreateUserDTO();
        createUserDTO.setCreateTime("132312312132");
        createUserDTO.setAvatar("231212");
        createUserDTO.setId("23123123");
        createUserDTO.setName("231312312");
        userService.createUser(createUserDTO);
    }

    @Test
    public void userFriendService() {
        UserFriend userFriend = new UserFriend();
        userFriend.setUserId("adfsa");
        userFriend.setFriendId("sdf");
        userFriend.setDelFlag(0);
        userFriendService.saveOrUpdate(userFriend);

    }

    @Test
    public void test111(){

    }
}

*/
