package com.ssafy.homer.user.config;

import com.ssafy.homer.user.domain.MyUserDetail;
import com.ssafy.homer.user.service.UserDetailServiceImpl;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
@EnableWebSecurity
public class SecurityConfig {


    /**
     * securityFilterChain을 생성하는 부분 -> 체인의 순서를 결정
     * @param http
     * @return
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http    .httpBasic().disable()
                .rememberMe().disable()
                .formLogin().disable()
                .csrf().disable()
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests((authorize) -> authorize
                        .anyRequest().authenticated()
                )
                //.oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
                /*.exceptionHandling((exceptions) -> exceptions
                        .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
                        .accessDeniedHandler(new BearerTokenAccessDeniedHandler())


                )*/;
                //경로 설정
                //필터 로직 설정 필요
        return http.build();
    }

    @Bean
    UserDetailsService users() {
        // @formatter:off
        return new InMemoryUserDetailsManager(
                MyUserDetail.builder()
                        .email("user@email.com")
                        .password("{noop}password")
                        .roles("app")
                        .build()
        );
        // @formatter:on
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }



}
