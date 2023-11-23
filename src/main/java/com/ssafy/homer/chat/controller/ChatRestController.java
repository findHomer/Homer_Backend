package com.ssafy.homer.chat.controller;

import java.util.List;

import com.ssafy.homer.user.repository.UserRepository;
import com.ssafy.homer.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssafy.homer.chat.model.dto.ChatMessage;
import com.ssafy.homer.chat.model.dto.ChatRoom;
import com.ssafy.homer.chat.model.service.ChatService;

import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Controller
@RequestMapping("/api/v1/chat")
public class ChatRestController {
	private final ChatService chatService;
	private final UserService userService;

	// 모든 채팅방 목록 반환
	@GetMapping("/rooms")
	public ResponseEntity<?> getRooms() {
		List<ChatRoom> result = chatService.getAllChatRoom();
		return ResponseEntity.ok(result);
	}

	// 채팅방 생성
	@PostMapping("/room/create")
	@ResponseBody
	public ChatRoom createRoom(@RequestParam String name) {
		return chatService.createChatRoom(name);
	}
	
	//채팅방 채팅 데이터 가져오기
	@Transactional
	@GetMapping("/room/{roomId}")
	public ResponseEntity<?> enterRoom(@PathVariable String roomId) {
		chatService.enterChatRoom(roomId);
		List<ChatMessage> chats = chatService.getAllChat(roomId);
		return ResponseEntity.ok(chats);
	}

	//사용자 정보 가지고 오기
	@GetMapping("/user")
	public ResponseEntity<?> getMyInfo() {
		return ResponseEntity.ok(userService.getMyInfo());
	}
	//채팅 관련은 전부 pub/sub으로 관리
}
