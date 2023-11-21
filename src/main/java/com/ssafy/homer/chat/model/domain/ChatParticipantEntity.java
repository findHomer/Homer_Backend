package com.ssafy.homer.chat.model.domain;

import com.ssafy.homer.user.domain.User;
import lombok.*;

import javax.persistence.*;

@Entity(name = "chat_participant")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatParticipantEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long chatParticipantId;

    @Column
    Long user_id;

    @Column
    Long chatroom_id;
}
