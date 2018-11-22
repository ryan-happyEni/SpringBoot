package com.example.demo.redis.repository.dao;

import java.time.LocalDateTime;

import com.example.demo.data.RewardItem;
import com.example.demo.data.RewardType;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class GameRewardRedisDao extends RedisDao{
	private RewardType rewardType;
	private String gameId;
	private String gameName;
	private String userId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime rewardTime;
	private RewardItem rewardItem;
	private String msg;
}
