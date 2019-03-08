package com.vm.im.dao.user;

import com.vm.im.common.vo.user.FindCurrentVO;
import com.vm.im.common.vo.user.FindUserVO;
import com.vm.im.entity.user.UserCurrentChat;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 用户当前会话 Mapper 接口
 * </p>
 *
 * @author zhangqi
 * @since 2019-03-02
 */
@Repository
public interface UserCurrentChatMapper extends BaseMapper<UserCurrentChat> {

    /**
     * 根据用户id 查询当前会话列表
     *
     * @param uid   用户id
     * @param count list size
     * @return
     */
    List<FindCurrentVO> listByUid(@Param("uid") String uid, @Param("count") int count);

    /**
     * 模糊查找用户
     *
     * @param uid
     * @param condition
     * @return
     */
    List<FindUserVO> findUser(@Param("uid") String uid, @Param("condition") String condition);


    List<String> fingFriendByUid(@Param("userId") String userId);

    List<UserCurrentChat> selectByFriendId(String friendId);

    void updateUserMessage(@Param("name") String name , @Param("friendId") String friendId ,
                           @Param("nickname") String nickname);
}
