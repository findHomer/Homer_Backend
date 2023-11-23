package com.ssafy.homer.chat.model.domain;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity(name = "chatroom")
public class ChatroomEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    String chatroomId;

    @Column
    String chatroomName;

    @Column
    Timestamp createdAt;
}
