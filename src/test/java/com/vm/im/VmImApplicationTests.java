/*

package com.vm.im;

import com.vm.im.common.dto.admin.CreateUserDTO;
import com.vm.im.common.vo.user.FindCurrentVO;
import com.vm.im.dao.group.ChatGroupMapper;
import com.vm.im.dao.user.UserChatGroupMapper;
import com.vm.im.dao.user.UserCurrentChatMapper;
import com.vm.im.dao.user.UserFriendMapper;
import com.vm.im.entity.user.UserFriend;
import com.vm.im.service.group.ChatGroupService;
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
    @Autowired
    private ChatGroupService chatGroupService;

    @Autowired
    private ChatGroupMapper chatGroupMapper;
    @Autowired
    private UserCurrentChatMapper userCurrentChatMapper;

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

        */
/*List<UserFriend> userFriend = userFriendMapper.selectByFriendId("1",0);
        System.out.println(userFriend);*//*



        //serFriendMapper.updateUserMessage("test","2","123");


        //List<UserChatVO> userChatVOS = userChatGroupMapper.selectByPrimaryKey("180160");

        //List<UserChatGroupVO> userChatGroupVOS = userChatGroupMapper.selectByUidAndGid("180160", "1");

        //List<UserMsgVO> userMsgVOS = userFriendMapper.selectByPrimaryKey("1", "1");
        //List<FindCurrentVO> findCurrentVOS = userCurrentChatMapper.listByUid("180179", 500);
        //System.out.println(findCurrentVOS);

        //userFriendService.updateUserMessage("nihao1","100083","nidao");
        //System.out.println(userMsgVOS);

        //List<String> list = userCurrentChatMapper.fingFriendByUid("180160");
        //List<UserChatVO> userChatVOS = userChatGroupMapper.selectByPrimaryKey("1");
        List<FindCurrentVO> userCurrentChats = userCurrentChatMapper.listByUid("180179",500);
        System.out.println(userCurrentChats);

    }
}

*/
