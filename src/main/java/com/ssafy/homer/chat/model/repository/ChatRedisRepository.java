package com.ssafy.homer.chat.model.repository;

import com.ssafy.homer.chat.model.domain.ChatEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRedisRepository extends CrudRepository<ChatEntity, Long> {
}
