package com.example.demo.memdb.table;

import java.util.LinkedHashMap;
import java.util.Map;

import com.example.demo.memdb.table.dao.GameDao;

public class GameTable{

	private static Map<String, GameDao> GAME;
	static {
		GAME = new LinkedHashMap<String, GameDao>();
	}
	
	public static Map<String, GameDao> getInstance(){
		return GAME;
	}
	
	public static GameDao get(String pk) {
		return GAME.get(pk);
	}
	
	public static int insert(GameDao vo, String pk) {
		int result = 0;
		if(GAME.get(pk)==null) {
			GAME.put(pk, vo);
			result = 1;
		}else {
			result = -1;
		}
		return result;
	}
	
	public static int update(GameDao vo, String pk) {
		int result = 0;
		if(GAME.get(pk)==null) {
			result = -1;
		}else {
			GAME.put(pk, vo);
			result = 1;
		}
		return result;
	}
	
	public static int delete(String pk) {
		int result = 0;
		if(GAME.get(pk)==null) {
			result = -1;
		}else {
			GAME.remove(pk);
			result = 1;
		}
		return result;
	}
}
