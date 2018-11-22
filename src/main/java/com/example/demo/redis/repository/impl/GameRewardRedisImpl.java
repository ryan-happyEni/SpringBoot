package com.example.demo.redis.repository.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.example.demo.data.RewardItem;
import com.example.demo.data.RewardType;
import com.example.demo.redis.repository.GameRewardRedis;
import com.example.demo.redis.repository.Redis;
import com.example.demo.redis.repository.dao.GameRewardRedisDao;

@Component
public class GameRewardRedisImpl extends Redis implements GameRewardRedis {
	private String mainKey="game:reward";
	
	public void addReward(GameRewardRedisDao dao) {
		addGame(dao.getRewardType(), dao.getGameId());
		addReward(dao.getRewardType(), dao.getGameId(), dao.getUserId(), dao.getRewardItem(), dao.getMsg());
	}
	
	private void addGame(RewardType rewardType, String gameId) {
		String mainKey = this.mainKey+":"+rewardType;
    	setOperations.add(mainKey, gameId);
        hashOperations.put(mainKey+":"+gameId, "gameId", gameId);
	}
	
	private void addReward(RewardType rewardType, String gameId, String userId, RewardItem rewardItem, String msg) {
		addSearch(rewardType, gameId, userId, rewardItem, msg);
		addInfomation(rewardType, gameId, userId, rewardItem, msg);
	}
	
	private void addSearch(RewardType rewardType, String gameId, String userId, RewardItem rewardItem, String msg) {
		String mainKey = this.mainKey+":"+rewardType;
		LocalDateTime now = LocalDateTime.now();
    	zSetOperations.add(mainKey+":game"+gameId+":user", userId, getTimeMillisecondDesc(now));
    	zSetOperations.add(mainKey+":all:user", gameId+"|"+userId, getTimeMillisecondDesc(now));
	}
	
	private void addInfomation(RewardType rewardType, String gameId, String userId, RewardItem rewardItem, String msg) {
		String mainKey = this.mainKey+":"+rewardType;
		LocalDateTime now = LocalDateTime.now();
        hashOperations.put(mainKey+":game"+gameId+":useri"+userId, "rewardTime", now.toString());
        hashOperations.put(mainKey+":game"+gameId+":useri"+userId, "rewardItem", ""+rewardItem);
        hashOperations.put(mainKey+":game"+gameId+":useri"+userId, "msg", ""+msg);
	}
	
	public List<GameRewardRedisDao> findUsers(GameRewardRedisDao dao, long start, long end, boolean isAsc) {
		String mainKey = this.mainKey+":"+dao.getRewardType();
		List<GameRewardRedisDao> list = new ArrayList<GameRewardRedisDao>();
		String searchKey = mainKey+":game"+dao.getGameId()+":user";
		boolean isAll=false;
		if(dao.getGameId()==null || dao.getGameId().equals("")) {
			searchKey = mainKey+":all:user";
			isAll=true;
		}

		Set<String> entries = null;
		if(isAsc) {
			entries = zSetOperations.reverseRange(searchKey, start, end);
		}else {
			entries = zSetOperations.range(searchKey, start, end);
		}
		if(entries!=null && entries.size()>0) {
			GameRewardRedisDao param = new GameRewardRedisDao();
			param.setRewardType(dao.getRewardType());
			param.setGameId(dao.getGameId());
			for(String entry : entries) {
				if(isAll) {
					param.setGameId(entry.indexOf("|")>0?entry.substring(0, entry.indexOf("|")):"");
					param.setUserId(entry.indexOf("|")>0?entry.substring(entry.indexOf("|")+1):entry);
				}else {
					param.setUserId(entry);
				}
				list.add(findUser(param));
			}
			if(list.size()>0) {
				GameRewardRedisDao ddo = list.get(0);
				if(ddo==null) {
					ddo = new GameRewardRedisDao();
					ddo.setGameId(dao.getGameId());
					ddo.setRewardType(dao.getRewardType());
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
	
	public GameRewardRedisDao findUser(GameRewardRedisDao dao) {
		String mainKey = this.mainKey+":"+dao.getRewardType();
		String searchKey = mainKey+":game"+dao.getGameId()+":useri"+dao.getUserId();
        Map<String, String> entry = hashOperations.entries(searchKey);

        if(entry!=null && entry.size()>0) {
    		GameRewardRedisDao data = new GameRewardRedisDao();
    		data.setRewardType(dao.getRewardType());
    		data.setGameId(dao.getGameId());
    		data.setUserId(dao.getUserId());
    		data.setRewardTime(entry.get("rewardTime")!=null?LocalDateTime.parse(entry.get("rewardTime")):null);
    		data.setRewardItem(RewardItem.valueOf(entry.get("rewardItem")));
    		data.setMsg(entry.get("msg"));
    		return data;
        }else {
        	return null;
        }
	}
}
