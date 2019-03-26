package com.vm.im.service.common;

import com.alibaba.fastjson.JSONObject;
import com.vm.im.common.dto.user.ChatHistoryDTO;
import com.vm.im.common.vo.user.ChatHistoryVO;
import com.vm.im.entity.common.Message;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 信息表 服务类
 * </p>
 *
 * @author zhangqi
 * @since 2019-02-20
 */
public interface MessageService extends IService<Message> {

    void saveMessage(JSONObject param,Long createTime);

    /**
     * 聊天历史
     *
     * @param chatHistoryDTO
     * @return
     */
    List<ChatHistoryVO> chatHistory(ChatHistoryDTO chatHistoryDTO);
}
