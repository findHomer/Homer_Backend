
package com.ssafy.homer.chat.model.repository;

import com.ssafy.homer.chat.model.domain.ChatroomEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatroomRepository extends JpaRepository<ChatroomEntity, String> {
}
