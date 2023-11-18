package com.ssafy.homer.user.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ssafy.homer.user.domain.MyUserDetail;
import com.ssafy.homer.user.domain.User;
import com.ssafy.homer.user.jwt.JwtUtil;
import com.ssafy.homer.user.repository.UserRepository;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter{

	private final JwtUtil jwtUtil;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String servletPath = request.getServletPath();
        // header 에서 JWT token을 가져옵니다.
        String authorizationHeader = request.getHeader("AUTHORIZATION");

        
            // Access Token만 꺼내옴
            String accessToken = null;
            if (StringUtils.hasText(authorizationHeader))
                accessToken = authorizationHeader.substring("Bearer ".length());
            else{//permitall 통과
                SecurityContextHolder.getContext().setAuthentication(null);
                filterChain.doFilter(request, response);
                return;
            }
            // === Access Token 검증 === //

            Claims jwtClaim = jwtUtil.verifyAccessToken(accessToken);
            //권한 claim에 추가해 db 호출 줄일 수 있음(개선필요)
            String email = jwtClaim.getSubject();
            String authority = jwtClaim.get("roles",String.class);
            System.out.println(authority);
            //User user = userRepository.findByEmail(email).orElseThrow(()-> new RuntimeException());
           // MyUserDetail userDetails = new MyUserDetail(user);
            Collection<GrantedAuthority> authorities = new ArrayList<>();

            for(String role : authority.split(",")){
                authorities.add(new SimpleGrantedAuthority(role));
            }
            //user를 securityContext에 principal로 등록
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, null, authorities);//authority
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            filterChain.doFilter(request, response);
        

    }
		
	

}
