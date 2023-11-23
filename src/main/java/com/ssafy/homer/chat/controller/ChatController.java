package com.ssafy.homer.chat.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import com.ssafy.homer.chat.model.dto.ChatMessage;
import com.ssafy.homer.chat.model.repository.ChatRoomRedisRepository;
import com.ssafy.homer.chat.model.service.ChatService;
import com.ssafy.homer.chat.model.service.RedisPublisher;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Controller
public class ChatController {

 private final RedisPublisher redisPublisher;
 private final ChatRoomRedisRepository chatRoomRepository;
 private final ChatService chatService;

 /**
  * websocket "/pub/chat/message"로 들어오는 메시징을 처리한다.
  */
 @MessageMapping("/chat/message")
 public void message(ChatMessage message) {
     chatService.sendChat(message);
 }
}