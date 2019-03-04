package com.vm.im.dao.user;

import com.vm.im.entity.user.UserChatGroup;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
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
@Mapper
@Repository
public interface UserChatGroupMapper extends BaseMapper<UserChatGroup> {

    List<UserChatGroup> selectByGroupId(String groupId);

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

    List<UserChatGroup> selectByPrimaryKey(@Param("userId")String userId, @Param("delFlag")Integer delFlag);
}
