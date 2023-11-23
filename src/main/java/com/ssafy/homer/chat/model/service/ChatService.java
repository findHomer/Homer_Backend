package com.ssafy.homer.chat.model.service;

import java.util.List;

import com.ssafy.homer.chat.model.dto.ChatMessage;
import com.ssafy.homer.chat.model.dto.ChatRoom;

public interface ChatService {
	//채팅방 관련 
	List<ChatRoom> getAllChatRoom(); // 모든 채팅방 리스트 가지고 오기
	List<ChatRoom> getChatRoomList(int pgno, int itemNum); // 몇 개씩 끊어서 채팅방 가져오기
	ChatRoom createChatRoom(String name); // chat 만들기
	void enterChatRoom(String roomId); // chat 들어가기
	
	//채팅 관련
	List<ChatMessage> getAllChat(String chatroomId); 
	List<ChatMessage> getChatMessages(String chatroomId, int pgno, int itemNum); 
	boolean sendChat(ChatMessage message);// 채팅 전송
	boolean deleteChat(Long chatId); // 채팅ID로 채팅 삭제
	
}
