package com.ssafy.homer.chat.model.dto;

import java.io.Serializable;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessage{
	/**
	 * 
	 */
//	private static final long serialVersionUID = -1445846086349013883L;
	/**
	 * 
	 */
	
	public enum MessageType{
		ENTER, TALK
	}
	
	private MessageType type;
	private String roomId;
	private String sender;
	private String message;
}
