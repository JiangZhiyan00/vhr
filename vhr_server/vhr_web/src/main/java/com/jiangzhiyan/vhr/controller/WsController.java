package com.jiangzhiyan.vhr.controller;

import com.jiangzhiyan.vhr.model.ChatMessage;
import com.jiangzhiyan.vhr.model.Hr;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

import java.util.Date;

/**
 * 聊天消息处理器
 * @author JiangZhiyan
 */
@Api("聊天消息处理")
@Controller
public class WsController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @ApiOperation("聊天消息处理")
    @MessageMapping("/ws/chat")
    public void handleMsg(Authentication authentication, ChatMessage chatMessage){
        Hr hr = (Hr) authentication.getPrincipal();
        chatMessage.setFrom(hr.getUsername());
        chatMessage.setFromNickname(hr.getName());
        chatMessage.setSendTime(new Date());
        simpMessagingTemplate.convertAndSendToUser(chatMessage.getTo(),"/queue/chat",chatMessage);
    }
}
