package com.ssafy.homer.user.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ssafy.homer.user.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.homer.user.domain.MyUserDetail;
import com.ssafy.homer.user.domain.User;
import com.ssafy.homer.user.dto.LoginDto;
import com.ssafy.homer.user.jwt.JwtUtil;

import lombok.RequiredArgsConstructor;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter{
	private final RedisService redisService;
	
	private final AuthenticationManager authenticationManager;
	private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(RedisService redisService,AuthenticationManager authenticationManager,JwtUtil jwtUtil){
        this.redisService = redisService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        super.setFilterProcessesUrl("/api/v1/login");
    }
    
	
	/**
     * 로그인 인증 시도
     */
    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws AuthenticationException {

    	// JSON 데이터를 LoginDto 객체로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        LoginDto loginDto;
		try {
			loginDto = objectMapper.readValue(request.getInputStream(), LoginDto.class);
			
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
	                loginDto.getEmail(),
	                loginDto.getPassword()
	        );
			
			return authenticationManager.authenticate(authenticationToken);//userdetailsService를 통해 인증 확인!!(Manager는 provider를 관리하는데 기본설정으로 DaoProvider가 사용된다.)
			  
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//에러 던지기
			return null;
		}
          }

    /**
     * 인증(로그인) 성공시 호출
     */
    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult
    ) throws IOException {

        MyUserDetail user = (MyUserDetail) authResult.getPrincipal();

        String accessToken = jwtUtil.createAccessToken(user.getUser());
        String refreshToken = jwtUtil.createRefreshToken(user.getUser());
         // Redis에 Refresh Token 저장
        redisService.saveRefreshToken(user.getUsername(),refreshToken);

        response.setContentType("application/json");
        
        Map<String, String> token = new HashMap<>();
        token.put("accessToken", accessToken);
        response.setContentType("application/json");
        
        
        // Refresh Token을 HttpOnly 쿠키로 설정
        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
        // expires in 30 days
        refreshTokenCookie.setMaxAge(30 * 24 * 60 * 60);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setPath("/"); // 쿠키 경로 설정
        // 필요한 경우 도메인, 유효기간 등을 설정
        response.addCookie(refreshTokenCookie);
        new ObjectMapper().writeValue(response.getWriter(),token);

    }

    /**
     * 인증(로그인) 실패시 호출
     */
    @Override
    protected void unsuccessfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException failed
    ) throws IOException {
        response.setStatus(404);
        response.setContentType("application/json");

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("errorCode", 404);
        body.put("errorMsg", "회원 정보 없음");

        new ObjectMapper().writeValue(response.getOutputStream(), body);
    }
}

