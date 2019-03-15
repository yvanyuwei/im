package com.vm.im.dao.user;

import com.vm.im.common.vo.user.FindUserVO;
import com.vm.im.common.vo.user.UserChatGroupVO;
import com.vm.im.common.vo.user.UserChatVO;
import com.vm.im.entity.user.UserChatGroup;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;


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


    /**
     * 模糊查找用户
     *
     * @param groupId
     * @param condition
     * @return
     */
    List<FindUserVO> findUser(@Param("groupId") String groupId, @Param("condition") String condition);

    /**
     *                  根据用户ID查询工会列表信息
     * @param groupId   组id
     * @return          返回当前用户id的工会列表
     */
    List<UserChatVO> selectByPrimaryKey(@Param("ChatGroupId")String groupId);


    /**
     *                     根据用户id和群id查询群成员信息列表
     * @param userId       用户id
     * @param chatGroupId  群id
     * @return             返回群成员信息列表
     */
    List<UserChatGroupVO> selectByUidAndGid(@Param("userId") String userId , @Param("chatGroupId") String chatGroupId);

    /**
     *                  根据好友ID查询好友列表信息
     * @param userId    好友id
     * @return          返回当前好友id的列表
     */
    List<UserChatGroup> selectByUserId(String userId);

    /**
     *                  更新用户信息
     * @param name      需要更新的昵称
     * @param groupId   好友id
     * @param nickname  给好友备注的名称/好友昵称
     */
    void updateUserMessage(@Param("name") String name, @Param("groupId") String groupId,
                      @Param("nickname") String nickname);


    List<String> selectGroupIdByUid(@Param("userId") String userId);

    List<String> selectUidByGroupId(@Param("chatGroupId") String groupId);


}
