package com.example.demo.web.service;

import java.util.List;

import com.example.demo.memdb.table.dao.GameDao;
import com.example.demo.redis.repository.dao.GameInstallRedisDao;
import com.example.demo.redis.repository.dao.GameReserveRedisDao;

public interface GameService {
	public List<GameDao> findAll(GameDao vo, String userId, String orderKey, boolean isAsc, boolean isClosed);
	public GameReserveRedisDao findReserve(GameReserveRedisDao vo);
	public int reserve(GameReserveRedisDao vo);
	public int cancel(GameReserveRedisDao vo);
	public int install(GameInstallRedisDao vo);
	public int reserveCnt(String gameId);
	public long reserveNo(String gameId, String userId);
}
