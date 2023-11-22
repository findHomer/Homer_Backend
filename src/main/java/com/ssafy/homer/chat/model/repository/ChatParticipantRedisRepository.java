package com.ssafy.homer.chat.model.repository;

import com.ssafy.homer.chat.model.domain.ChatParticipantEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatParticipantRedisRepository extends CrudRepository<ChatParticipantEntity, Long> {
}
