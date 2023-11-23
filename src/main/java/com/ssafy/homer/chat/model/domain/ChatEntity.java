package com.ssafy.homer.chat.model.domain;


import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "chat")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long chatId;

    @Column
    String chatroomId;

    @Column
    Long userId;

    @Column(columnDefinition = "text")
    String contents;

    @CreationTimestamp
    @Column
    Timestamp sendTime;
}
