package com.vm.im.dao.group;

import com.vm.im.entity.group.ChatGroup;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vm.im.entity.user.UserChatGroup;
import com.vm.im.entity.user.UserFriend;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * <p>
 * 聊天群 Mapper 接口
 * </p>
 *
 * @author zhangqi
 * @since 2019-02-20
 */
@Mapper
@Repository
public interface ChatGroupMapper extends BaseMapper<ChatGroup> {

    List<ChatGroup> selectAllGroup();
}
