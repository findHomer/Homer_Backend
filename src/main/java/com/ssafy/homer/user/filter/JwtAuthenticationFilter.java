package com.ssafy.homer.user.filter;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ssafy.homer.user.jwt.JwtUtil;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter{
//config 에서 의존하는 Bean을 주입
	private final AuthenticationManager authenticationManager;
	//private final RedisService redisService;
	private final JwtUtil jwtUtil;
}
