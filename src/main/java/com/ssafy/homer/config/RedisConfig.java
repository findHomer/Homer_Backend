package com.ssafy.homer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableRedisRepositories
public class RedisConfig {

	@Value("${spring.redis.port}")
	private int port;

	@Value("${spring.redis.host}")
	private String host;

	/**
	 * redis pub/sub 메시지를 처리하는 listener 설정
	 */
	@Bean
	public RedisMessageListenerContainer redisMessageListener(RedisConnectionFactory connectionFactory) {
		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		return container;
	}

	@Bean
	public RedisConnectionFactory redisConnectionFactory() {
		LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(host, port);
		return lettuceConnectionFactory;
	}

	/**
	 * 어플리케이션에서 사용할 redisTemplate 설정
	 */
	 @Bean
	    public RedisTemplate<?, ?> redisTemplate() {//redis와 통신시 사용
	        RedisTemplate<byte[], byte[]> redisTemplate = new RedisTemplate<>();
	        redisTemplate.setConnectionFactory(redisConnectionFactory());
	        redisTemplate.setKeySerializer(new StringRedisSerializer());
	        return redisTemplate;
	    }
}