package com.example.demo.redis.repository;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.demo.redis.repository.dao.GameReserveRedisDao;

@Component
public interface GameReserveRedis {
	public void addReserve(GameReserveRedisDao dao);
	public List<GameReserveRedisDao> findUsers(GameReserveRedisDao dao, long start, long end, boolean isAsc);
	public GameReserveRedisDao findUser(GameReserveRedisDao dao);
	public void removeReserve(GameReserveRedisDao dao);
}
