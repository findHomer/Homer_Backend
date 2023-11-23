package com.ssafy.homer.chat.model.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "chatroom")
@Builder
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class ChatroomEntity {
    @Id
    String chatroomId;

    @Column
    String chatroomName;

    @Column
    String profileUrl;
    
    @Column(columnDefinition = "text")
    String lastChat;
    
    @Column
    String lastChatter;

    @UpdateTimestamp
    Timestamp lastUpdated;

    @CreationTimestamp
    Timestamp createdAt;
}
