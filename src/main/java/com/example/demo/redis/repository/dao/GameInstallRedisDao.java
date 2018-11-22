package com.example.demo.redis.repository.dao;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class GameInstallRedisDao extends RedisDao {
	public String pk;
	public String gameId;
	public String userId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public LocalDateTime installTime;
}
