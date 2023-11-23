package com.ssafy.homer.chat.model.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Repository;

import com.ssafy.homer.chat.model.dto.ChatMessage;
import com.ssafy.homer.chat.model.service.RedisSubscriber;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class ChatRedisRepository {
    private final RedisMessageListenerContainer redisMessageListener;
    // 구독 처리 서비스
    private final RedisSubscriber redisSubscriber;
    // Redis
    private static final String CHAT = "CHAT";
    private final RedisTemplate<String, ChatMessage> redisTemplate;
    private ListOperations<String, ChatMessage> opsListChat;
    // 채팅방의 대화 메시지를 발행하기 위한 redis topic 정보. 서버별로 채팅방에 매치되는 topic정보를 Map에 넣어 roomId로 찾을수 있도록 한다.
    private Map<String, ChannelTopic> topics;

    @PostConstruct
    private void init() {
    	opsListChat = redisTemplate.opsForList();
    }

    // 모든 채팅 가지고 오기
    public List<ChatMessage> findAllChat(String roomId) {
        return opsListChat.range(CHAT+roomId, 0, -1);
    }

    /**
     * 채팅 전송
     */
    public void sendChat(String roomId, ChatMessage message) {
        opsListChat.rightPush(CHAT+roomId, message);
    }

}