package com.ssafy.homer.chat.model.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

import com.ssafy.homer.chat.model.dto.ChatMessage;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class RedisPublisher {
 private final RedisTemplate<String, Object> redisTemplate;

 public void publish(ChannelTopic topic, ChatMessage message) {
     redisTemplate.convertAndSend(topic.getTopic(), message);
 }
}