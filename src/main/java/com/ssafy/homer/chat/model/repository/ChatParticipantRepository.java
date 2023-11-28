package com.ssafy.homer.chat.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.homer.chat.model.domain.ChatParticipantEntity;

@Repository
public interface ChatParticipantRepository extends JpaRepository<ChatParticipantEntity, Long> {
}
