package com.vm.im.dao.user;

import com.vm.im.entity.user.UserChatGroup;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 用户群 Mapper 接口
 * </p>
 *
 * @author zhangqi
 * @since 2019-02-19
 */
@Mapper
@Repository
public interface UserChatGroupMapper extends BaseMapper<UserChatGroup> {

    List<UserChatGroup> selectByGroupId(String groupId);
}
