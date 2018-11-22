package com.example.demo.redis.repository;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.demo.redis.repository.dao.GameInstallRedisDao;

@Component
public interface GameInstallRedis {
	public void addHist(GameInstallRedisDao dao);
	public List<GameInstallRedisDao> findUsers(GameInstallRedisDao dao, long start, long end, boolean isAsc);
	public GameInstallRedisDao findUser(GameInstallRedisDao dao);
}
