package com.example.demo.admin.service;

import java.util.List;

import com.example.demo.memdb.table.dao.GameDao;

public interface AdminGameService {
	public List<GameDao> findAll(GameDao vo, String sortKey, boolean isAsc);
	public List<GameDao> findAllOpendGame(GameDao vo, String sortKey, boolean isAsc);
	public List<GameDao> findAllClosedGame(GameDao vo, String sortKey, boolean isAsc);
	public GameDao find(String game_id);
	public void insert(GameDao vo);
	public void update(GameDao vo);
	public void delete(GameDao vo);
	public int count();
}
