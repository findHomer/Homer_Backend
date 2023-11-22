package com.ssafy.homer.user.jwt;

import java.util.Date;

import com.ssafy.homer.exception.BaseException;
import com.ssafy.homer.exception.ErrorCode;
import org.springframework.stereotype.Component;

import com.ssafy.homer.user.domain.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Component
public class JwtUtil {
	
	public static String SECRET_KEY = "12321313asdsadsadadsadsadsadsadsadsadsa";
	
	private static final Long accessTokenExpireTime = 1000L*60*60;// 1시간
	
	private static final Long refreshTokenExpireTime = 1000L*30*60*60*24; // 30일
	/**
     * user로 토큰 생성
     * HEADER : alg, kid
     * PAYLOAD : sub, iat, exp
     * SIGNATURE : JwtKey.getRandomKey로 구한 Secret Key로 HS512 해시
     *
     * @param User 유저
     * @return jwt accesstoken
     */
	public String createAccessToken(User user) {
        Claims claims = Jwts.claims().setSubject(user.getEmail()); // subject
        claims.put("roles", user.getRole());
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
	
	
	public String createRefreshToken(User user) {
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
	
	public Claims verifyAccessToken(String accessToken) {
		try{
			return Jwts.parserBuilder()
	                .setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
	                .build()
	                .parseClaimsJws(accessToken)
	                .getBody();
		
		} catch (SecurityException e) {
	        log.info("Invalid JWT signature.");
			throw new BaseException(ErrorCode.UNAUTHORIZED);
	        //throw new CustomJwtRuntimeException();
	    } catch (MalformedJwtException e) {
	        log.info("Invalid JWT token.");
			throw new BaseException(ErrorCode.UNAUTHORIZED);
	        //throw new CustomJwtRuntimeException();
	    } catch (ExpiredJwtException e) {
	        log.info("Expired JWT token.");
			throw new BaseException(ErrorCode.UNAUTHORIZED);
	       // throw new CustomJwtRuntimeException();
	    } catch (UnsupportedJwtException e) {
	        log.info("Unsupported JWT token.");
			throw new BaseException(ErrorCode.UNAUTHORIZED);
	        //throw new CustomJwtRuntimeException();
	    } catch (IllegalArgumentException e) {
	        log.info("JWT token compact of handler are invalid.");
			throw new BaseException(ErrorCode.UNAUTHORIZED);
	       // throw new CustomJwtRuntimeException();
	    }
		

	}

	public Claims verifyRefreshToken(String accessToken) {
		try{
			return Jwts.parserBuilder()
					.setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
					.build()
					.parseClaimsJws(accessToken)
					.getBody();
		//임시
		} catch (Exception e) {
			throw new BaseException(ErrorCode.REFRESHTOKEN_ERROR);
		}


	}
	public String getEmail(String token) {
		try{
			return Jwts.parserBuilder()
					.setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
					.build()
					.parseClaimsJws(token)
					.getBody().getSubject();

		} catch (SecurityException e) {
			log.info("Invalid JWT signature.");
			//throw new CustomJwtRuntimeException();
		} catch (MalformedJwtException e) {
			log.info("Invalid JWT token.");
			//throw new CustomJwtRuntimeException();
		} catch (ExpiredJwtException e) {
			log.info("Expired JWT token.");
			// throw new CustomJwtRuntimeException();
		} catch (UnsupportedJwtException e) {
			log.info("Unsupported JWT token.");
			//throw new CustomJwtRuntimeException();
		} catch (IllegalArgumentException e) {
			log.info("JWT token compact of handler are invalid.");
			// throw new CustomJwtRuntimeException();
		}

		return null;
	}
	
}
