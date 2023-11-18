package com.ssafy.homer.user.config;

import com.ssafy.homer.user.domain.MyUserDetail;
import com.ssafy.homer.user.filter.JwtAuthenticationFilter;
import com.ssafy.homer.user.filter.JwtAuthorizationFilter;
import com.ssafy.homer.user.jwt.JwtUtil;
import com.ssafy.homer.user.repository.UserRepository;
import com.ssafy.homer.user.service.UserDetailServiceImpl;

import lombok.RequiredArgsConstructor;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig{
	
	private final JwtUtil jwtUtil;
	
    /**
     * securityFilterChain을 생성하는 부분 -> 체인의 순서를 결정
     * @param http
     * @return
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	
    	AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);

        http    .httpBasic().disable()
                .rememberMe().disable()
                .formLogin().disable()
                .csrf().disable()
                .apply(new MyCustomDsl()) // 커스텀 필터 등록
				.and()
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests((authorize) -> authorize
                        // /api 시작하는 URL에 대한 접근 제어
                		.antMatchers("/api/v1/users/ping","/api/v1/login","/api/v1/users/signup").permitAll()
                        .antMatchers("/api/v1/users/**").hasAnyRole("USER", "ADMIN")
                        // /admin 시작하는 URL에 대한 접근 제어
                        .antMatchers("/api/v1/admin/**").hasRole("ADMIN")
                        // 나머지 URL에 대한 접근 허용
                        .anyRequest().permitAll()
                    );
                //.oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
                /*.exceptionHandling((exceptions) -> exceptions
                        .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
                        .accessDeniedHandler(new BearerTokenAccessDeniedHandler())


                )*/
                //경로 설정
                //필터 로직 설정 필요
        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();//Bcrypt 해쉬 알고리즘 사용
    }



    public class MyCustomDsl extends AbstractHttpConfigurer<MyCustomDsl, HttpSecurity> {
		@Override
		public void configure(HttpSecurity http) throws Exception {
			AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
			http
					
				.addFilterAt(new JwtAuthenticationFilter(authenticationManager,jwtUtil), UsernamePasswordAuthenticationFilter.class)//usernamePasswordAuthenticationFilter위치에 커스텀한 인증필터 대체
				.addFilterAfter(new JwtAuthorizationFilter(jwtUtil), JwtAuthenticationFilter.class);
            }
	}
    
}