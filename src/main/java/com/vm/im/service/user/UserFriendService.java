package com.vm.im.service.user;

import com.vm.im.entity.user.User;
import com.vm.im.entity.user.UserFriend;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户好友 服务类
 * </p>
 *
 * @author zhangqi
 * @since 2019-02-19
 */
@Service
public interface UserFriendService extends IService<UserFriend> {

    List<UserFriend> selectByPrimaryKey();
}
