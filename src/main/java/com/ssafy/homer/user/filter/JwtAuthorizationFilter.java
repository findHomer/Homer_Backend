package com.ssafy.homer.user.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ssafy.homer.user.domain.MyUserDetail;
import com.ssafy.homer.user.domain.User;
import com.ssafy.homer.user.jwt.JwtUtil;
import com.ssafy.homer.user.repository.UserRepository;
import com.sun.org.apache.xml.internal.security.algorithms.JCEMapper.Algorithm;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter{
	
	private final UserRepository userRepository;
	private final JwtUtil jwtUtil;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String servletPath = request.getServletPath();
        // header 에서 JWT token을 가져옵니다.
        String authorizationHeader = request.getHeader("AUTHORIZATION");

        
            // Access Token만 꺼내옴
            String accessToken = null;
            if (authorizationHeader != null)
                accessToken = authorizationHeader.substring("Bearer ".length());
            else{//permitall 통과
                SecurityContextHolder.getContext().setAuthentication(null);
                filterChain.doFilter(request, response);
                return;
            }
            // === Access Token 검증 === //

            Claims jwtClaim = jwtUtil.verifyAccessToken(accessToken);
            String email = jwtClaim.getSubject();

            User user = userRepository.findByEmail(email).orElseThrow(()-> new RuntimeException());
            MyUserDetail userDetails = new MyUserDetail(user);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null, userDetails.getAuthorities());//authority
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            filterChain.doFilter(request, response);
        

    }
		
	

}
