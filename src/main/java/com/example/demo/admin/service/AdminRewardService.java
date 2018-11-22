package com.example.demo.admin.service;

import java.util.List;

import com.example.demo.redis.repository.dao.GameRewardRedisDao;

public interface AdminRewardService {
	public List<GameRewardRedisDao> findAll(GameRewardRedisDao vo, String sortKey, boolean isAsc, String gameId);
	public void settle(String gameId);
}
