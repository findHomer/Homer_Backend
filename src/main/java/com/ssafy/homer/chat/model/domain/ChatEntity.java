package com.ssafy.homer.chat.model.domain;


import lombok.*;
import org.hibernate.annotations.GeneratorType;

import javax.persistence.*;
import java.sql.Timestamp;

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

    @Column
    Timestamp sendTime;
}
