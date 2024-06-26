package com.ssafy.homer.chat.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.homer.chat.model.domain.ChatEntity;

@Repository
public interface ChatRepository extends JpaRepository<ChatEntity, Long> {
	List<ChatEntity> findByChatroomId(String chatroomId); 
}
