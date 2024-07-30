package com.ssafy.homer.apartInfo.service;

import com.ssafy.homer.apartInfo.dto.RankingDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@Slf4j
public class RankingRedisService {

    private final StringRedisTemplate redisTemplate;
    private final ZSetOperations<String, String> zSetOperations;
    private final HashOperations<String, String, String> hashOperations;

    private final String ranking ="rank";

    @Autowired
    public RankingRedisService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.zSetOperations = redisTemplate.opsForZSet();
        this.hashOperations = redisTemplate.opsForHash();
    }

    @Scheduled(cron = "0 0/10 * * * ?")
    public void decreaseViewCount(){
        log.info("viewCount 감소 배치 작업 실행");
        LocalTime threeHoursAgo = LocalTime.now().minus(3, ChronoUnit.HOURS);
        int minute = (threeHoursAgo.getMinute() / 10) * 10;
        String blockTime = String.format("%02d:%02d", threeHoursAgo.getHour(), minute);

        //3시간 전 조회수 감소
        Map<String,String> aptIdAndViewCount = hashOperations.entries(blockTime);
        for (String aptId : aptIdAndViewCount.keySet()) {
            String viewCount = aptIdAndViewCount.get(aptId);
            try {
                long viewCountValue = Long.parseLong(viewCount);
                zSetOperations.incrementScore(ranking, aptId, -viewCountValue);
            } catch (NumberFormatException e) {
                log.error("view Count가 숫자형식으로 되어있지 않습니다.");
            }
        }
        //hashkey 삭제
        redisTemplate.delete(blockTime);


        log.info("viewCount 감소 배치 작업 완료");
    }

    public void addOrUpdateId( String id) {

        zSetOperations.incrementScore(ranking, id, 1);
    }

    public void addOrUpdateIdByUnit(String id) {

        // 현재 시간 기준으로 10분 단위 블록 키 생성
        LocalTime now = LocalTime.now();
        int minute = (now.getMinute() / 10) * 10;
        String blockTime = String.format("%02d:%02d",now.getHour(),minute);  // 10분 단위로 내림
        // 10분 단위 블록의 aptId 조회수 증가
        hashOperations.increment(blockTime, id, 1);
    }

    public Double getScore(String id) {
        return zSetOperations.score(ranking, id);
    }

    public List<RankingDto> getTopRanks(int count) {

        Set<ZSetOperations.TypedTuple<String>> resultSet = zSetOperations.reverseRangeWithScores(ranking, 0, count - 1);
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
