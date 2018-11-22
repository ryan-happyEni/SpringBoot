package com.example.demo.redis.repository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import javax.annotation.Resource;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

@Component
public abstract class Redis {
    @Resource(name="redisTemplate")
	protected RedisTemplate<String, String> redisTemplate;

    @Resource(name="redisTemplate")
    protected ValueOperations<String, String> valueOperations;

    @Resource(name = "redisTemplate")
    protected ListOperations<String, String> listOperations;

    @Resource(name = "redisTemplate")
    protected HashOperations<String, String, String> hashOperations;

    @Resource(name = "redisTemplate")
    protected SetOperations<String, String> setOperations;

    @Resource(name="redisTemplate")
    protected ZSetOperations<String, String> zSetOperations;

    public long getTimeMillisecondDesc(LocalDateTime time) {
    	return LocalDateTime.parse("9999-12-31T00:00:00").until(time, ChronoUnit.MILLIS);
    }

    public long getTimeMillisecondAsc(LocalDateTime time) {
    	return time.until(LocalDateTime.parse("9999-12-31T00:00:00"), ChronoUnit.MILLIS);
    }
}
