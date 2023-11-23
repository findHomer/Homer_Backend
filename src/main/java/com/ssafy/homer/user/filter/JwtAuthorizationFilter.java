package com.ssafy.homer.user.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ssafy.homer.exception.BaseException;
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
        String accessToken = request.getHeader("AUTHORIZATION");

        System.out.println(accessToken);
        String[] notPermitList= {"/api/v1/users/logout", "/api/v1/bookmarks", "/api/v1/users/mypage", "/api/v1/users/admin", "/api/v1/users/profiles","/api/v1/chat"};
        boolean nonpass=true;

        for(String url:notPermitList){
            if(servletPath.startsWith(url)) {
                nonpass = false;
                break;
            }
        }

        //permitAll 처리
        if (nonpass){
            SecurityContextHolder.getContext().setAuthentication(null);
            filterChain.doFilter(request, response);
            return;
            }
            if(accessToken.length()>"Bearer".length())
                accessToken = accessToken.substring("Bearer ".length());
            try {
                // === Access Token 검증 === //

                Claims jwtClaim = jwtUtil.verifyAccessToken(accessToken);

                //권한 claim에 추가해 db 호출 줄일 수 있음(개선필요)
                String email = jwtClaim.getSubject();
                String authority = jwtClaim.get("roles", String.class);

                Collection<GrantedAuthority> authorities = new ArrayList<>();

                for (String role : authority.split(",")) {
                    authorities.add(new SimpleGrantedAuthority(role));
                }
                //user를 securityContext에 principal로 등록
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, null, authorities);//authority
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                filterChain.doFilter(request, response);
            }catch (BaseException e){
                // 예외에 대한 응답 코드 및 메시지 설정
                int statusCode = e.getErrorCode().getErrorCode();
                String errorMessage = e.getErrorCode().getErrorMsg();

                // HTTP 응답 설정
                response.setStatus(statusCode);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");

                // 응답 본문에 오류 메시지 작성
                try (PrintWriter writer = response.getWriter()) {
                    writer.write("{\"error\": \"" + errorMessage + "\"}");
                }
        }

    }
		
	

}
