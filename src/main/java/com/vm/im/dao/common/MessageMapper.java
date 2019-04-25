package com.vm.im.dao.common;

import com.vm.im.common.dto.user.ChatHistoryDTO;
import com.vm.im.common.vo.user.ChatHistoryVO;
import com.vm.im.entity.common.Message;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

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


    /**
     * 查询单聊聊天历史
     *
     * @param chatHistoryDTO
     * @return
     */
    List<ChatHistoryVO> listSingleByUidAndToid(@Param("dto") ChatHistoryDTO chatHistoryDTO);

    /**
     * 查询群聊聊天历史
     *
     * @param chatHistoryDTO
     * @return
     */
    List<ChatHistoryVO> listGroupByUidAndToid(@Param("dto") ChatHistoryDTO chatHistoryDTO);
}
