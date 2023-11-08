package com.ssafy.homer.chat.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRoom {
    private String roomId;
    private String name;

    public static ChatRoom create(String name) {
    	ChatRoom chatRoom = new ChatRoom();
    	chatRoom.roomId = name;
    	chatRoom.name = name;
    	return chatRoom;
    }
}