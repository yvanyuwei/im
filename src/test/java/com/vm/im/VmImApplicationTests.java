package com.vm.im;

import com.alibaba.fastjson.JSON;
import com.vm.im.dao.user.UserMapper;
import com.vm.im.entity.user.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VmImApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Autowired
    private UserMapper userMapper;
    @Test
    public void test1(){
        User user = userMapper.selectByUserName("test");
        System.out.println(JSON.toJSONString(user));
        User user1 = userMapper.selectByPrimaryKey("1");
        System.out.println(JSON.toJSONString(user1));
    }
}

