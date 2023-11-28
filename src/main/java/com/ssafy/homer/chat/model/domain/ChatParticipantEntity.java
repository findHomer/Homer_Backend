package com.ssafy.homer.chat.model.domain;

import com.ssafy.homer.user.domain.User;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.*;

@Entity(name = "chat_participant")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
//@RedisHash(value = "chat_participant")
public class ChatParticipantEntity {

    @org.springframework.data.annotation.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long chatParticipantId;

    @Column
    Long user_id;

    @Column
    String chatroom_id;
}
