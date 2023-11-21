package com.ssafy.homer.user.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ssafy.homer.user.domain.Role;
import com.ssafy.homer.user.domain.User;
import com.ssafy.homer.user.dto.MyPageDto;
import com.ssafy.homer.user.dto.SignupDto;
import com.ssafy.homer.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService{

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	@Override
	public void signup(SignupDto signupDto) {
		Optional<User> user = userRepository.findByEmail(signupDto.getEmail());//중복예외 던져줌
		System.out.println(passwordEncoder.encode(signupDto.getPassword()));
		if(user.isPresent()) {
			//중복예외처리
			return;
		}
		
		User newUser =User.builder()
				.email(signupDto.getEmail())
				.name(signupDto.getName())
				.birth(signupDto.getBirth())
				.nickname(signupDto.getNickname())
				.role(Role.USER.getValue())
				.password(passwordEncoder.encode(signupDto.getPassword()))
				.build();
		userRepository.save(newUser);
	}
	
	@Override
	public void logout() {
		//redis refresh토큰 삭제
		
	}

	@Override
	public MyPageDto getMyInfo() {
		// TODO Auto-generated method stub
		return null;
	}

}
