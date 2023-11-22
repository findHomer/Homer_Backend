package com.ssafy.homer.chat.model.repository;

import com.ssafy.homer.chat.model.domain.ChatroomEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatroomRedisRepository extends CrudRepository<ChatroomEntity, Long> {
}
