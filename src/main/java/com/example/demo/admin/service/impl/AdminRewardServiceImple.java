package com.example.demo.admin.service.impl;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.admin.service.AdminRewardService;
import com.example.demo.data.RewardItem;
import com.example.demo.data.RewardType;
import com.example.demo.memdb.selector.GameMemSelector;
import com.example.demo.memdb.table.dao.GameDao;
import com.example.demo.memdb.table.dao.ReserveDao;
import com.example.demo.redis.repository.GameRewardRedis;
import com.example.demo.redis.repository.dao.GameRewardRedisDao;

@Service(value="AdminRewardService")
public class AdminRewardServiceImple implements AdminRewardService{

	@Autowired
	private GameMemSelector gameSelector;
	
    @Autowired
    GameRewardRedis gameRewardRedis;

	public List<GameRewardRedisDao> findAll(GameRewardRedisDao vo, String orderKey, boolean isAsc, String gameId){
		List<GameRewardRedisDao> list = gameRewardRedis.findUsers(vo, vo.getStartIndex(), vo.getEndIndex(), false);
		if(list!=null && list.size()>0) {
			for(GameRewardRedisDao data : list) {
				GameDao game = gameSelector.find(data.getGameId());
				data.setGameName(game!=null?game.getGameName():"삭제된 게임.");
			}
		}
		return list;
	}

	public void settle(String gameId) {
		GameDao game = gameSelector.find(gameId);
		if(game!=null) {
			Map<String, ReserveDao> reserveUser = game.getReserveUser();
			Map<String, LocalDateTime> rewardUser = game.getReserveRewardHist();
			Set<String> keys = null;
			if(rewardUser!=null) {
				Set<String> allUser = reserveUser.keySet();
				keys = new HashSet<String>();

				for(String key : allUser) {
					if(!rewardUser.containsKey(key)) {
						keys.add(key);
					}
				}
			}else {
				keys =  reserveUser.keySet();
			}
			
			if(keys!=null) {

				for(String key : keys) {
					game.addReserveUser(key);
					game.addReserveRewardHist(key);
					
    		    	GameRewardRedisDao grrd = new GameRewardRedisDao();
    		    	grrd.setRewardType(RewardType.RESERVE);
    		    	grrd.setGameId(gameId);
    		    	grrd.setUserId(key);
    		    	grrd.setRewardItem(RewardItem.EMOTICON);
    		    	grrd.setMsg("사전예약 보상");
    		    	gameRewardRedis.addReward(grrd);
    				
    				long u = game.getReserveUser().get(key).getIndex();
    				
    				if(u==100 || u==500 || u==1000 || u==5000 || u%10000==0) {
        		    	grrd = new GameRewardRedisDao();
        		    	grrd.setRewardType(RewardType.LOTTO);
        		    	grrd.setGameId(gameId);
        		    	grrd.setUserId(key);
        		    	grrd.setRewardItem(RewardItem.EMOTICON);
        		    	grrd.setMsg("사전예약 "+u+"번째 참여 보상 ");
        		    	gameRewardRedis.addReward(grrd);
    				}
				}
			}
		}
	}
}
