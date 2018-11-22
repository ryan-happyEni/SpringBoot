package com.example.demo.redis.repository;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.demo.redis.repository.dao.GameRewardRedisDao;

@Component
public interface GameRewardRedis {
	public void addReward(GameRewardRedisDao dao);
	public List<GameRewardRedisDao> findUsers(GameRewardRedisDao dao, long start, long end, boolean isAsc);
	public GameRewardRedisDao findUser(GameRewardRedisDao dao);
}
