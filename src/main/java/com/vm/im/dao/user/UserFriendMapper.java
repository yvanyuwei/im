package com.vm.im.dao.user;

import com.vm.im.common.vo.user.FindUserVO;
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

    List<UserFriend> selectByPrimaryKey(@Param("userId") String userId, @Param("friendId") String friendId, @Param(
            "delFlag") Integer delFlag);

    void updateUserMessage(@Param("nickname") String name ,@Param("friendId") String friend_id,
                           @Param("nickname") String nickname);

    List<UserFriend> selectByFriendId(@Param("friendId") String friendId, @Param("delFlag") Integer delFlag);
}
