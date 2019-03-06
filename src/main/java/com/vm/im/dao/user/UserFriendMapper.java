package com.vm.im.dao.user;

import com.vm.im.common.vo.user.FindUserVO;
import com.vm.im.common.vo.user.UserMsgVO;
import com.vm.im.entity.user.UserFriend;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 用户好友 Mapper 接口
 * </p>
 *
 * @author zhangqi
 * @since 2019-02-19
 */
@Mapper
@Repository
public interface UserFriendMapper extends BaseMapper<UserFriend> {

    /**
     * 模糊查找用户
     *
     * @param uid
     * @param condition
     * @return
     */
    List<FindUserVO> findUser(@Param("uid") String uid, @Param("condition") String condition);

    /**
     *                  查询用户好友列表
     * @param userId    用户id
     * @param friendId  还有id
     * @return          好友列表信息
     */
    List<UserMsgVO> selectByPrimaryKey(@Param("userId") String userId, @Param("friendId") String friendId);

    /**
     *                  更新用户信息
     * @param name      需要更新的昵称
     * @param friend_id 好友id
     * @param nickname  给好友备注的名称/好友昵称
     */
    void updateUserMessage(@Param("nickname") String name ,@Param("friendId") String friend_id,
                           @Param("nickname") String nickname);

    /**
     *                  根据好友ID查询好友列表信息
     * @param friendId  好友id
     * @param delFlag   状态情况
     * @return          返回当前好友id的列表
     */
    List<UserFriend> selectByFriendId(@Param("friendId") String friendId, @Param("delFlag") Integer delFlag);
}
