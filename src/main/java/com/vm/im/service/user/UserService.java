package com.vm.im.service.user;

import com.vm.im.common.dto.user.FindUserDTO;
import com.vm.im.common.util.ResponseJson;
import com.vm.im.entity.user.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpSession;
import java.util.List;

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
     * 根据查找目标体查找用户
     *
     * @param findUserDTO
     * @param uid
     * @return
     */
    List<User> findUserList(FindUserDTO findUserDTO, String uid);

    /**
     * 模糊查找用户
     *
     * @param condition
     * @return
     */
    List<User> findUser(String condition);
}
