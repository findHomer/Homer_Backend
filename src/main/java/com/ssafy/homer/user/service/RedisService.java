package com.ssafy.homer.user.service;

import com.ssafy.homer.user.dto.RefreshTokenDto;
import com.ssafy.homer.user.repository.RedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final RedisRepository tokenRedisRepository;

    public void saveRefreshToken(String email, String refreshToken){

        tokenRedisRepository.save(new RefreshTokenDto(email,refreshToken));
    }

    public void deleteRefreshToken(String email){

        tokenRedisRepository.deleteById(email);



    }
}
