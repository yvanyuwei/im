package com.vm.im.dao.common;

import com.vm.im.entity.common.Message;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 信息表 Mapper 接口
 * </p>
 *
 * @author zhangqi
 * @since 2019-02-20
 */
@Mapper
@Repository
public interface MessageMapper extends BaseMapper<Message> {

}
