package com.ssafy.homer.user.filter;

import java.io.IOException;
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
import org.springframework.web.filter.OncePerRequestFilter;

import com.ssafy.homer.user.jwt.JwtUtil;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String servletPath = request.getServletPath();
        // header 에서 JWT token을 가져옵니다.
        String accessToken = request.getHeader("AUTHORIZATION");


        //permitAll 처리
        if (accessToken == null || accessToken.isEmpty()) {
            SecurityContextHolder.getContext().setAuthentication(null);
            filterChain.doFilter(request, response);
            return;
        }
        if (accessToken.length() > "Bearer".length())
            accessToken = accessToken.substring("Bearer ".length());
        try {
            // === Access Token 검증 === //
            Claims jwtClaim = jwtUtil.verifyAccessToken(accessToken);

            String email = jwtClaim.getSubject();
            String authority = jwtClaim.get("roles", String.class);

            Collection<GrantedAuthority> authorities = new ArrayList<>();

            for (String role : authority.split(",")) {
                authorities.add(new SimpleGrantedAuthority(role));
            }

            //user를 securityContext에 principal로 등록
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            filterChain.doFilter(request, response);
        } catch (BaseException e) {
            int statusCode = e.getErrorCode().getErrorCode();

            // HTTP 응답 설정
            response.setStatus(statusCode);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

        }

    }


}
