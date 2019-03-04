package com.vm.im.service.user;

import com.vm.im.common.util.ResponseJson;
import com.vm.im.entity.user.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpSession;
import java.io.IOException;


/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author zhangqi
 * @since 2019-02-19
 */
public interface UserService extends IService<User> {

    ResponseJson login(String username, String password, HttpSession session);

    ResponseJson getByUserId(String userId);

    /**
     * 保存或更新用户信息
     */
    void saveUserInfo();
}
