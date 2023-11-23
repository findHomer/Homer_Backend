package com.ssafy.homer.chat.model.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ssafy.homer.chat.model.domain.ChatEntity;
import com.ssafy.homer.chat.model.domain.ChatroomEntity;
import com.ssafy.homer.chat.model.dto.ChatMessage;
import com.ssafy.homer.chat.model.dto.ChatRoom;
import com.ssafy.homer.chat.model.repository.ChatParticipantRepository;
import com.ssafy.homer.chat.model.repository.ChatRedisRepository;
import com.ssafy.homer.chat.model.repository.ChatRepository;
import com.ssafy.homer.chat.model.repository.ChatRoomRedisRepository;
import com.ssafy.homer.chat.model.repository.ChatroomRepository;
import com.ssafy.homer.user.domain.User;
import com.ssafy.homer.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ChatServiceImpl implements ChatService {
	// repository
	// mysql
	private final ChatRepository chatRepository;
	private final ChatroomRepository chatroomRepository;
	private final ChatParticipantRepository chatParticipantRepository;
	private final UserRepository userRepository;

	// redis
	private final ChatRoomRedisRepository chatroomRedisRepository;
	private final ChatRedisRepository chatRedisRepository;
	private final RedisPublisher redisPublisher;

	@Override
	public List<ChatRoom> getAllChatRoom() {
		return chatroomRedisRepository.findAllRoom();
		// TODO mysql과 연동
	}

	@Override
	public ChatRoom createChatRoom(String name) {
		// 채팅룸에 관한 내용을 redis에 올린다.
		ChatRoom chatroom = chatroomRedisRepository.createChatRoom(name);

		// 해당 내용 그대로 mysql에 저장
		ChatroomEntity entity = ChatroomEntity.builder().chatroomId(chatroom.getRoomId())
				.chatroomName(chatroom.getName()).build();

		chatroomRepository.save(entity);
		return chatroom;
	}

	@Override
	public List<ChatRoom> getChatRoomList(int pgno, int itemNum) {
		// 레디스에 그만큼 개수가 올라갔는지 확인
		Optional<List<ChatRoom>> redisRepo = Optional.ofNullable(chatroomRedisRepository.findAllRoom());

		// 없으면 mysql 조사

		// 그래도 없으면 그냥 반환
		return null;
	}

	@Override
	public void enterChatRoom(String roomId) {
		// redis에 채팅방이 있는지 확인한다.
		Optional<ChatRoom> chat = Optional.ofNullable(chatroomRedisRepository.findRoomById(roomId));

		// 있으면 접속을 수행한다.
		if (chat.isPresent()) {
			chatroomRedisRepository.enterChatRoom(roomId);
			return;
		}

		// 없으면 mysql에 있는지 확인하고 가지고 온다.
		Optional<ChatroomEntity> chatroom = chatroomRepository.findById(roomId);

		// 채팅방이 존재한다면 가지고 와서 접속한다.
		if (chatroom.isPresent()) {
			ChatRoom dto = ChatRoom.builder().roomId(chatroom.get().getChatroomId())
					.name(chatroom.get().getChatroomName()).build();

			// redis에 올린다.
			chatroomRedisRepository.reenterChatRoom(dto);
			return;
		}

		// 참여 관계를 mysql에 저장한다.

	}

	@Override
	public boolean sendChat(ChatMessage message) {
		// redis로 먼저 pub 시키고
		if (ChatMessage.MessageType.ENTER.equals(message.getType())) {
			enterChatRoom(message.getRoomId());
			message.setMessage(message.getSender() + "님이 입장하셨습니다.");
		}
		// Websocket에 발행된 메시지를 redis로 발행한다(publish)
		redisPublisher.publish(chatroomRedisRepository.getTopic(message.getRoomId()), message);
		chatRedisRepository.sendChat(message.getRoomId(), message); // redis에 저장

		// 해당하는 챗을 mysql로 저장한다.
		Optional<User> user = userRepository.findByEmail(message.getSender());

		ChatEntity chat = ChatEntity.builder()
				.chatroomId(message.getRoomId())
				.contents(message.getMessage())
				.userId(user
						.orElse(User.builder()
								.userId(0L)
								.build())
						.getUserId())
				.build();

		chatRepository.save(chat);

		return true;
	}

	@Override
	public List<ChatMessage> getAllChat(String chatroomId) {
		// redis 확인하기
		List<ChatMessage> msgs = chatRedisRepository.findAllChat(chatroomId);

		// redis에 있으면 전달
		if (msgs != null) {
			return msgs;
		}

		// 없으면 mysql에서 가지고 오기

		return msgs;
	}

	@Override
	public boolean deleteChat(Long chatId) {
		// 레디스 데이터 있다면 제거

		// mysql에서 채팅 삭제
		chatRepository.deleteById(chatId);
		return true;
	}

	@Override
	public List<ChatMessage> getChatMessages(String chatroomId, int pgno, int itemNum) {
		// TODO Auto-generated method stub
		return null;
	}

}
