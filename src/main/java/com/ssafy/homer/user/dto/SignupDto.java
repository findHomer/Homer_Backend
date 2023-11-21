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
public class SignupDto {
	private String name;
	private LocalDate birth;
	private String email;
	private String password;
	private String nickname;
}
