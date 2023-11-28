package com.ssafy.homer.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;


@Builder
@Getter
@AllArgsConstructor
@RedisHash(value="refreshToken" ,timeToLive=60*60*24*30)//30일 기간
public class RefreshTokenDto {

    @Id
    private String email;
    private String token;
}
