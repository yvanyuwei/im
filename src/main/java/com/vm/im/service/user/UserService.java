package com.vm.im.service.user;

import com.vm.im.common.dto.admin.CreateUserDTO;
import com.vm.im.common.dto.admin.UserInfoDTO;
import com.vm.im.common.dto.user.FindUserDTO;
import com.vm.im.common.util.ResponseJson;
import com.vm.im.common.vo.user.FindUserVO;
import com.vm.im.entity.user.User;
import com.baomidou.mybatisplus.extension.service.IService;
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

    //ResponseJson login(String username, String password, HttpSession session);

    ResponseJson getByUserId(String userId);

    /**
     * 保存或更新用户信息
     */
    void saveUserInfo(User user/*JSONObject param, ChannelHandlerContext ctx*/);

    /**
     * 根据查找目标体查找用户
     *
     * @param findUserDTO
     * @param uid
     * @return
     */
    List<FindUserVO> findUserList(FindUserDTO findUserDTO, String uid);

    /**
     * 模糊查找用户
     *
     * @param condition
     * @return
     */
    List<FindUserVO> findUser(String condition);

    /**
     * 创建添加用户
     * @param createUserDTO
     */
    void createUser(CreateUserDTO createUserDTO);

    /**
     * 更新用户信息
     *
     * @param userInfoDTO
     */
    void updateUserInfo(UserInfoDTO userInfoDTO);

    /**
     * 校验用户有效性
     *
     * @param userId
     */
    User checkUser(String userId);
}