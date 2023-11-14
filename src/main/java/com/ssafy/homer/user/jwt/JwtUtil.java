package com.ssafy.homer.user.jwt;

import org.springframework.stereotype.Component;

import com.ssafy.homer.user.domain.User;
import io.jsonwebtoken.*;

@Component
public class JwtUtil {
	
	public String SECRET_KEY = "12321313";
	
	private final Long accessTokenExpireTime = 1000L*60*60;// 1시간
	
	private final Long refreshTokenExpireTime = 1000L*30*60*60*24; // 30일
	/**
     * user로 토큰 생성
     * HEADER : alg, kid
     * PAYLOAD : sub, iat, exp
     * SIGNATURE : JwtKey.getRandomKey로 구한 Secret Key로 HS512 해시
     *
     * @param User 유저
     * @return jwt accesstoken
     */
	public static String createAccessToken(User user) {
        Claims claims = Jwts.claims().setSubject(user.getEmail()); // subject
        Date now = new Date(); // 현재 시간
       
        // JWT Token 생성

        return Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + accessTokenExpireTime)) // 토큰 만료 시간 설정
                .setHeaderParam(JwsHeader.KEY_ID, "JWT") // kid
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()),SignatureAlgorithm.HS256) // signature
                .compact();
    }
	
	
	public static String createRefreshToken(User user) {
        Claims claims = Jwts.claims().setSubject(user.getEmail()); // subject
        Date now = new Date(); // 현재 시간
       
        // JWT Token 생성

        return Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + refreshTokenExpireTime)) // 토큰 만료 시간 설정
                .setHeaderParam(JwsHeader.KEY_ID, "JWT") // kid
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()),SignatureAlgorithm.HS256) // signature
                .compact();
    }
}
