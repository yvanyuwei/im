package com.vm.im.dao.user;

import com.vm.im.entity.user.UserChatGroup;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 用户群 Mapper 接口
 * </p>
 *
 * @author zhangqi
 * @since 2019-02-19
 */
@Repository
public interface UserChatGroupMapper extends BaseMapper<UserChatGroup> {

    /**
     * 更新用户群组信息为删除
     *
     * @param groupId
     */
    void deleteByGroupId(String groupId);

    /**
     * 根据群id 用户id 查询群用户数据
     *
     * @param groupId
     * @param userId
     * @return
     */
    UserChatGroup selectUserByGroupIdAndUid(@Param("groupId") String groupId, @Param("userId") String userId);
}
