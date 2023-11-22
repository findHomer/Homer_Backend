package com.ssafy.homer.chat.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import com.ssafy.homer.chat.model.dto.ChatMessage;
import com.ssafy.homer.chat.model.repository.ChatRoomRepository;
import com.ssafy.homer.chat.model.service.RedisPublisher;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Controller
public class ChatController {

 private final RedisPublisher redisPublisher;
 private final ChatRoomRepository chatRoomRepository;

 /**
  * websocket "/pub/chat/message"로 들어오는 메시징을 처리한다.
  */
 @MessageMapping("/chat/message")
 public void message(ChatMessage message) {
     if (ChatMessage.MessageType.ENTER.equals(message.getType())) {
         chatRoomRepository.enterChatRoom(message.getRoomId());
         message.setMessage(message.getSender() + "님이 입장하셨습니다.");
     }
     // Websocket에 발행된 메시지를 redis로 발행한다(publish)
     redisPublisher.publish(chatRoomRepository.getTopic(message.getRoomId()), message);
 }
}