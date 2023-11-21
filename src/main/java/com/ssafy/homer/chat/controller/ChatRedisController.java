package com.ssafy.homer.chat.controller;

import com.ssafy.homer.chat.model.service.ChatRedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class ChatRedisController {
    private final ChatRedisService chatRedisService;
}
