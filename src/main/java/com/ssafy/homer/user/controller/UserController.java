package com.ssafy.homer.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.homer.user.dto.SignupDto;
import com.ssafy.homer.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;
	
	@PostMapping("/signup")
	public ResponseEntity signup(@RequestBody SignupDto signupDto) {
		System.out.println(signupDto);
		userService.signup(signupDto);
		
		return ResponseEntity.ok().build();
	}
	
	@PostMapping("/logout")
	public ResponseEntity logout() {
		userService.logout();
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/mypage")
	public ResponseEntity mypage() {
		//userService.getMyInfo();
		return ResponseEntity.ok().build();
	}
	
	/**
	 * 프로필 추가하는 부분
	 * @return
	 */
	@PostMapping("/profiles")
	public ResponseEntity addProfile() {
		//userService.addProfile()
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/ping")
	public ResponseEntity pong() {
		return ResponseEntity.ok("pong");
	}
	
}
