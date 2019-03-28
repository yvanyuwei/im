package com.vm.im.service.common.impl;

import com.alibaba.fastjson.JSONObject;
import com.vm.im.common.dto.user.ChatHistoryDTO;
import com.vm.im.common.dto.user.FindUserDTO;
import com.vm.im.common.enums.ChatTypeEnum;
import com.vm.im.common.enums.UserChatTypeEnum;
import com.vm.im.common.vo.user.ChatHistoryVO;
import com.vm.im.common.vo.user.FindUserVO;
import com.vm.im.entity.common.Message;
import com.vm.im.dao.common.MessageMapper;
import com.vm.im.service.common.MessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vm.im.service.group.ChatGroupService;
import com.vm.im.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 信息表 服务实现类
 * </p>
 *
 * @author zhangqi
 * @since 2019-02-20
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {
    private static final Logger LOG = LoggerFactory.getLogger(MessageServiceImpl.class);

    @Autowired
    private MessageMapper messageMapper;

    @Lazy
    @Autowired
    private UserService userService;

    @Autowired
    private ChatGroupService chatGroupService;

    @Async
    public void saveMessage(JSONObject param,Long createTime) {
        Message msg = new Message();
        msg.setCreateTime(new Date(createTime));
        msg.setFromId(String.valueOf(param.get("fromUserId")));
        String type = String.valueOf(param.get("type"));
        if (type.equals(ChatTypeEnum.SINGLE_SENDING.name())){
            String toUserId = String.valueOf(param.get("toUserId"));
            msg.setToId(toUserId);
            msg.setType(1);
        }else if(type.equals(ChatTypeEnum.GROUP_SENDING.name())){
            String toGroupId = String.valueOf(param.get("toGroupId"));
            msg.setToId(toGroupId);
            msg.setType(3);
        }
        String content = String.valueOf(param.get("content"));
        if (content.length() > 1000){
            content = content.substring(0,1000);
        }
        msg.setContent(content);
        save(msg);
    }

    /**
     * 查询聊天历史
     *
     * @param chatHistoryDTO
     * @return
     */
    @Override
    public List<ChatHistoryVO> chatHistory(ChatHistoryDTO chatHistoryDTO) {
        UserChatTypeEnum userChatTypeEnum = UserChatTypeEnum.valueOf(chatHistoryDTO.getType());
        List<ChatHistoryVO> result = new ArrayList<>();

        // TODO  聊天历史消息 红包 解析有点问题
        switch (userChatTypeEnum) {
            case SINGLE:
                userService.checkUser(chatHistoryDTO.getToId());
                result = messageMapper.listSingleByUidAndToid(chatHistoryDTO);
                break;
            case GROUP:
                chatGroupService.checkChatGroup(chatHistoryDTO.getFromId(), chatHistoryDTO.getToId());
                result = messageMapper.listGroupByUidAndToid(chatHistoryDTO);
                break;
            default:
                LOG.info("用户聊天类型异常, typeEnum:{}", userChatTypeEnum);
                break;
        }

        return result;
    }

    /**
     * 根据查找目标体查找用户
     *
     * @param findUserDTO
     * @param uid
     * @return
     */
    @Override
    public List<FindUserVO> findUserList(FindUserDTO findUserDTO, String uid) {
        return userService.findUserList(findUserDTO, uid);
    }
}
