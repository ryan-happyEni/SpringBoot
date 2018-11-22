package com.example.demo.redis.repository.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.example.demo.redis.repository.GameInstallRedis;
import com.example.demo.redis.repository.Redis;
import com.example.demo.redis.repository.dao.GameInstallRedisDao;

@Component
public class GameInstallRedisImpl extends Redis implements GameInstallRedis {
	private String mainKey="game:install";
	
	public void addHist(GameInstallRedisDao dao) {
		addGame(dao.getGameId());
		addGameRserve(dao.getGameId(), dao.getUserId(), dao.getInstallTime());
	}
	
	private void addGame(String gameId) {
    	setOperations.add(mainKey, gameId);
        hashOperations.put(mainKey+":game"+gameId, "gameId", gameId);
	}
	
	private void addGameRserve(String gameId, String userId, LocalDateTime installTime) {
    	String pk = UUID.randomUUID()+"";
    	addSearch(pk, gameId, userId, installTime);
    	addInfomation(pk, gameId, userId, installTime);
	}
	
	private void addSearch(String pk, String gameId, String userId, LocalDateTime installTime) {
    	zSetOperations.add(mainKey+":game"+gameId+":user", pk, getTimeMillisecondDesc(installTime));
    	zSetOperations.add(mainKey+":all:user", gameId+"|"+pk, getTimeMillisecondDesc(installTime));
	}
	
	private void addInfomation(String pk, String gameId, String userId, LocalDateTime installTime) {
        hashOperations.put(mainKey+":game"+gameId+":useri"+pk, "userId", userId);
        hashOperations.put(mainKey+":game"+gameId+":useri"+pk, "installTime", installTime.toString());
	}
	
	public List<GameInstallRedisDao> findUsers(GameInstallRedisDao dao, long start, long end, boolean isAsc) {
		List<GameInstallRedisDao> list = new ArrayList<GameInstallRedisDao>();
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
			list = new ArrayList<GameInstallRedisDao>();
			GameInstallRedisDao param = new GameInstallRedisDao();
			param.setGameId(dao.getGameId());
			for(String entry : entries) {
				if(dao.getGameId()==null || dao.getGameId().equals("")) {
					param.setGameId(entry.indexOf("|")>0?entry.substring(0, entry.indexOf("|")):"");
					param.setPk(entry.indexOf("|")>0?entry.substring(entry.indexOf("|")+1):entry);
				}else {
					param.setPk(entry);
				}
				list.add(findUser(param));
			}
			if(list.size()>0) {
				GameInstallRedisDao ddo = list.get(0);
				if(ddo==null) {
					ddo = new GameInstallRedisDao();
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
	
	public GameInstallRedisDao findUser(GameInstallRedisDao dao) {
		String searchKey = mainKey+":game"+dao.getGameId()+":useri"+dao.getPk();
        Map<String, String> entry = hashOperations.entries(searchKey);
        
        if(entry!=null && entry.size()>0) {
    		GameInstallRedisDao data = new GameInstallRedisDao();
    		data.setGameId(dao.getGameId());
    		data.setPk(dao.getPk());
    		data.setUserId(entry.get("userId"));
    		data.setInstallTime(entry.get("installTime")!=null?LocalDateTime.parse(entry.get("installTime")):null);
    		return data;
        }else {
        	return null;
        }
	}
}
