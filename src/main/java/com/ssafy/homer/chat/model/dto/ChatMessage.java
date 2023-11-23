package com.ssafy.homer.chat.model.dto;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class
ChatMessage{

	public enum MessageType{
		ENTER, TALK
	}
	
	private MessageType type;
	private String roomId;
	private String sender;
	private String profileUrl;
	private String message;
	private Timestamp sendTime;
}
