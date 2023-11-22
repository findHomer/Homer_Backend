package com.ssafy.homer.user.controller;

import com.ssafy.homer.user.dto.MyPageDto;
import com.ssafy.homer.user.jwt.JwtUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.ssafy.homer.user.dto.SignupDto;
import com.ssafy.homer.user.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

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
	public ResponseEntity logout(HttpServletRequest request,HttpServletResponse response) {
		Cookie[] getCookie = request.getCookies();
		for(Cookie cookie : getCookie) {
			System.out.println(cookie.getName());
			cookie.setMaxAge(0);
			response.addCookie(cookie);
		}
		userService.logout();
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/mypage")
	public ResponseEntity mypage() {
		MyPageDto mypageDto = userService.getMyInfo();
		return ResponseEntity.ok().body(mypageDto);
	}
	
	/**
	 * 프로필 사진추가하는 부분
	 * @return
	 */
	@PatchMapping("/profiles")
	public ResponseEntity addProfile(@RequestPart(value = "image", required = false) MultipartFile multipartFile) {
		userService.addProfile(multipartFile);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/silent-refresh")
	public ResponseEntity refresh(@CookieValue String refreshToken){
		System.out.println(refreshToken);
		String accessToken = userService.refresh(refreshToken);

		return ResponseEntity.ok().body(accessToken);
	}


	@GetMapping("/ping")
	public ResponseEntity pong() {
		return ResponseEntity.ok("pong");
	}
	
}
