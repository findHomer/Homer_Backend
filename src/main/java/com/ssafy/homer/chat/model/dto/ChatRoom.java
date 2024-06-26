package com.ssafy.homer.chat.model.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

import javax.persistence.Column;

import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoom implements Serializable {

    private static final long serialVersionUID = 6494678977089006639L;

    private String roomId;
    private String name;

    private String profileUrl;
    private String lastChat;
    private String lastChatter;
    private Timestamp lastUpdated;

    
    public static ChatRoom create(String name) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.roomId = UUID.randomUUID().toString();
        chatRoom.name = name;
        return chatRoom;
    }
}