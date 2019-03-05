package com.vm.im;

import com.vm.im.dao.user.UserChatGroupMapper;
import com.vm.im.dao.user.UserFriendMapper;
import com.vm.im.entity.user.User;
import com.vm.im.entity.user.UserFriend;
import com.vm.im.service.user.UserChatGroupService;
import com.vm.im.service.user.UserFriendService;
import com.vm.im.service.user.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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

    @Test
    public void contextLoads() {
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
        /*List<UserFriend> userFriend = userFriendMapper.selectByFriendId("1",0);
        System.out.println(userFriend);*/
        userFriendMapper.updateUserMessage("test","2","123");
    }
}

