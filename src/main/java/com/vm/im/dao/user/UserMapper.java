package com.vm.im.dao.user;

import com.vm.im.entity.user.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author zhangqi
 * @since 2019-02-19
 */
@Mapper
@Repository
public interface UserMapper extends BaseMapper<User> {

    User selectByUserName(String name);

    int insertSelective(User record);

    /**
     * 模糊查找用户
     *
     * @param condition
     * @return
     */
    List<User> findUser(String condition);
}
