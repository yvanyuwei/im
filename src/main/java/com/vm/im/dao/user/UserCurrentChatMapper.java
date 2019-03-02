package com.vm.im.dao.user;

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
    List<UserCurrentChat> listByUid(@Param("uid") String uid, @Param("count") int count);
}
