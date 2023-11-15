package com.ssafy.homer.user.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MyPageDto {

	private String name;
	private String profileUrl;
	private LocalDate birth;
	private String nickname;
	
}
