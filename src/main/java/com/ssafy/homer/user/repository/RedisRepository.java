package com.ssafy.homer.user.repository;

import com.ssafy.homer.user.dto.RefreshTokenDto;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RedisRepository extends CrudRepository<RefreshTokenDto,String> {
    Optional<RefreshTokenDto> findById(String email);
}
