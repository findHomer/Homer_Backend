package com.ssafy.homer.user.service;

import com.ssafy.homer.user.dto.MyPageDto;
import com.ssafy.homer.user.dto.SignupDto;

public interface UserService {
	
	public void signup(SignupDto signupDto);

	public void logout();

	public MyPageDto getMyInfo();
}
