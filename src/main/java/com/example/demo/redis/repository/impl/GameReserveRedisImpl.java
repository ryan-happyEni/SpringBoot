package com.example.demo.redis.repository.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.example.demo.redis.repository.GameReserveRedis;
import com.example.demo.redis.repository.Redis;
import com.example.demo.redis.repository.dao.GameReserveRedisDao;

@Component
public class GameReserveRedisImpl extends Redis implements GameReserveRedis {
	private String mainKey="game:reserve";
	
	public void addReserve(GameReserveRedisDao dao) {
		addGame(dao.getGameId());
		addGameRserve(dao.getGameId(), dao.getUserId(), dao.getReserveTime(), dao.getReserveIndex());
	}
	
	private void addGame(String gameId) {
    	setOperations.add(mainKey, gameId);
        hashOperations.put(mainKey+":game"+gameId, "gameId", gameId);
	}
	
	private void addGameRserve(String gameId, String userId, LocalDateTime reserveTime, long reserveIndex) {
		addSearch(gameId, userId, reserveTime, reserveIndex);
    	addInfomation(gameId, userId, reserveTime, reserveIndex);
	}
	
	private void addSearch(String gameId, String userId, LocalDateTime reserveTime, long reserveIndex) {
    	zSetOperations.add(mainKey+":game"+gameId+":user", userId, getTimeMillisecondDesc(reserveTime));
    	zSetOperations.add(mainKey+":all:user", gameId+"|"+userId, getTimeMillisecondDesc(reserveTime));
	}
	
	private void addInfomation(String gameId, String userId, LocalDateTime reserveTime, long reserveIndex) {
        hashOperations.put(mainKey+":game"+gameId+":useri"+userId, "reserveTime", reserveTime.toString());
        hashOperations.put(mainKey+":game"+gameId+":useri"+userId, "reserveIndex", ""+reserveIndex);
	}
	
	public List<GameReserveRedisDao> findUsers(GameReserveRedisDao dao, long start, long end, boolean isAsc) {
		List<GameReserveRedisDao> list = new ArrayList<GameReserveRedisDao>();
		String searchKey = mainKey+":game"+dao.getGameId()+":user";
		if(dao.getGameId()==null || dao.getGameId().equals("")) {
			searchKey = mainKey+":all:user";
		}

		Set<String> entries = null;
		if(isAsc) {
			entries = zSetOperations.reverseRange(searchKey, start, end);
		}else {
			entries = zSetOperations.range(searchKey, start, end);
		}
		if(entries!=null && entries.size()>0) {
			GameReserveRedisDao param = new GameReserveRedisDao();
			param.setGameId(dao.getGameId());
			for(String entry : entries) {
				if(dao.getGameId()==null || dao.getGameId().equals("")) {
					param.setGameId(entry.indexOf("|")>0?entry.substring(0, entry.indexOf("|")):"");
					param.setUserId(entry.indexOf("|")>0?entry.substring(entry.indexOf("|")+1):entry);
				}else {
					param.setUserId(entry);
				}
				list.add(findUser(param));
			}
			if(list.size()>0) {
				GameReserveRedisDao ddo = list.get(0);
				if(ddo==null) {
					ddo = new GameReserveRedisDao();
					ddo.setGameId(dao.getGameId());
				}
				ddo.setTotalCnt(zSetOperations.zCard(searchKey));
				ddo.setPageNum(dao.getPageNum());
				ddo.setViewCnt(dao.getViewCnt());
				ddo.setTotalPage(dao.getTotalPage(ddo.getTotalCnt()));
				list.set(0, ddo);
			}
		}
        return list;
	}
	
	public GameReserveRedisDao findUser(GameReserveRedisDao dao) {
		String searchKey = mainKey+":game"+dao.getGameId()+":useri"+dao.getUserId();
        Map<String, String> entry = hashOperations.entries(searchKey);
        
        if(entry!=null && entry.size()>0) {
    		GameReserveRedisDao data = new GameReserveRedisDao();
    		data.setGameId(dao.getGameId());
    		data.setUserId(dao.getUserId());
    		data.setReserveTime(entry.get("reserveTime")!=null?LocalDateTime.parse(entry.get("reserveTime")):null);
    		data.setReserveIndex(Long.parseLong(entry.get("reserveIndex")));
    		return data;
        }else {
        	return null;
        }
	}
	
	public void removeReserve(GameReserveRedisDao dao) {
		String searchKey = mainKey+":game"+dao.getGameId()+":useri"+dao.getUserId();
		hashOperations.delete(searchKey, "reserveTime");
		hashOperations.delete(searchKey, "reserveIndex");

		zSetOperations.remove(mainKey+":game"+dao.getGameId()+":user", dao.getUserId());
		zSetOperations.remove(mainKey+":game"+dao.getGameId()+":user:asc", dao.getUserId());
    	zSetOperations.remove(mainKey+":all:user", dao.getGameId()+"|"+dao.getUserId());
    	zSetOperations.remove(mainKey+":all:user:asc", dao.getGameId()+"|"+dao.getUserId());
	}
}
