package com.ssafy.homer.user.service;

import com.ssafy.homer.user.dto.MyPageDto;
import com.ssafy.homer.user.dto.SignupDto;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
	
	public void signup(SignupDto signupDto);

	public void logout();

	public MyPageDto getMyInfo();

	public String refresh(String refreshToken);

	public void addProfile(MultipartFile multipartFile);
}
