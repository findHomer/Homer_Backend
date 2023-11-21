package com.ssafy.homer.chat.model.service;

import com.ssafy.homer.chat.model.repository.ChatParticipantRedisRepository;
import com.ssafy.homer.chat.model.repository.ChatRedisRepository;
import com.ssafy.homer.chat.model.repository.ChatroomRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ChatRedisServiceImpl implements ChatRedisService{
    private final ChatRedisRepository chatRedisRepository;
    private final ChatroomRedisRepository chatroomRedisRepository;
    private final ChatParticipantRedisRepository chatParticipantRedisRepository;


}
