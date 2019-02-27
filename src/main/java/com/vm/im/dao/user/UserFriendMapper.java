package com.vm.im.dao.user;

import com.vm.im.entity.user.UserFriend;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

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

    UserFriend selectByPrimaryKey();
}
