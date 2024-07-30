package com.ssafy.homer.apartInfo.service;

import com.ssafy.homer.apartInfo.dto.RankingDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class RankingRedisService {

    private final StringRedisTemplate redisTemplate;
    private final ZSetOperations<String, String> zSetOperations;
    private final HashOperations<String, String, String> hashOperations;

    @Autowired
    public RankingRedisService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.zSetOperations = redisTemplate.opsForZSet();
        this.hashOperations = redisTemplate.opsForHash();
    }

    public void addOrUpdateId(String key, String id) {

        zSetOperations.incrementScore(key, id, 1);
    }

    public void addOrUpdateIdByUnit(String key, String id) {

        // 현재 시간 기준으로 10분 단위 블록 키 생성
        LocalTime now = LocalTime.now();

        String blockTime = now.getHour()+":"+(now.getMinute() / 10) * 10;  // 10분 단위로 내림
        // 10분 단위 블록의 aptId 조회수 증가
        hashOperations.increment(blockTime, id, 1);
    }

    public Double getScore(String key, String id) {
        return zSetOperations.score(key, id);
    }

    public List<RankingDto> getTopRanks(String key, int count) {

        Set<ZSetOperations.TypedTuple<String>> resultSet = zSetOperations.reverseRangeWithScores(key, 0, count - 1);
        List<RankingDto> resultList = new ArrayList<>();

        for (ZSetOperations.TypedTuple<String> item : resultSet) {
            RankingDto rankingDto = RankingDto.builder()
                    .aptId(item.getValue())
                    .score(item.getScore().toString())
                    .build();

            resultList.add(rankingDto);
        }

        return resultList;
    }

}
