package com.ssafy.homer.apartInfo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

@Service
public class RankingRedisService {

    private final StringRedisTemplate redisTemplate;
    private final ZSetOperations<String, String> zSetOperations;

    @Autowired
    public RankingRedisService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.zSetOperations = redisTemplate.opsForZSet();
    }

    public void addOrUpdateId(String key, String id) {
        zSetOperations.incrementScore(key, id, 1);
    }

    public Double getScore(String key, String id) {
        return zSetOperations.score(key, id);
    }
}
