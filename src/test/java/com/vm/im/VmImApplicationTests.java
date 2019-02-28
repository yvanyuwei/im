package com.vm.im;

import com.vm.im.entity.user.UserFriend;
import com.vm.im.service.user.UserChatGroupService;
import com.vm.im.service.user.UserFriendService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class VmImApplicationTests {

    @Autowired
    UserChatGroupService userChatGroupService;

    @Autowired
    UserFriendService userFriendService;

    @Test
    public void contextLoads() {
    }

    @Test
    public void userFriendService() {
        UserFriend userFriend = new UserFriend();
        userFriend.setUserId("adfsa");
        userFriend.setFriendId("sdf");
        userFriendService.saveOrUpdate(userFriend);

    }
}

