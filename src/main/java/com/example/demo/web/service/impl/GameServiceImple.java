package com.example.demo.web.service.impl;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.example.demo.data.RewardItem;
import com.example.demo.data.RewardType;
import com.example.demo.memdb.selector.GameMemSelector;
import com.example.demo.memdb.table.GameTable;
import com.example.demo.memdb.table.dao.GameDao;
import com.example.demo.memdb.table.dao.ReserveDao;
import com.example.demo.redis.repository.GameInstallRedis;
import com.example.demo.redis.repository.GameReserveRedis;
import com.example.demo.redis.repository.GameRewardRedis;
import com.example.demo.redis.repository.dao.GameInstallRedisDao;
import com.example.demo.redis.repository.dao.GameReserveRedisDao;
import com.example.demo.redis.repository.dao.GameRewardRedisDao;
import com.example.demo.web.service.GameService;

@Service(value="GameService")
public class GameServiceImple implements GameService{

	@Autowired
	private GameMemSelector gameSelector;

    @Autowired
    GameReserveRedis gameReserveRedis;
    
    @Autowired
    GameInstallRedis gameInstallRedis;
    
    @Autowired
    GameRewardRedis gameRewardRedis;

	public List<GameDao> findAll(GameDao vo, String userId, String orderKey, boolean isAsc, boolean isClosed){
		Map<String, GameDao> map = GameTable.getInstance();
		Set<String> keys = map.keySet();
		Set<String> filterdKey = new HashSet<String>();
		for(String key : keys) {
			LocalDateTime time = map.get(key).getCloseTime();
			if(time!=null) {
				if(isClosed) {
					if(LocalDateTime.now().until(map.get(key).getCloseTime(), ChronoUnit.HOURS)<0) {
						filterdKey.add(key);
					}
				}else {
					if(LocalDateTime.now().until(map.get(key).getCloseTime(), ChronoUnit.HOURS)>0) {
						filterdKey.add(key);
					}
				}
			}
		}
		List<GameDao> list = gameSelector.getList(map, gameSelector.getSortedMap(orderKey, filterdKey), false, vo.getPageNum(), vo.getViewCnt());
		if(list!=null && list.size()>0) {
			GameDao dvo = list.get(0);
			dvo.setPageNum(vo.getPageNum());
			dvo.setTotalCnt(filterdKey.size());
			dvo.setTotalPage(gameSelector.getTotalPage(vo.getViewCnt(), dvo.getTotalCnt()));
			
			if(!userId.equals("")) {
				
				GameReserveRedisDao param = new GameReserveRedisDao();
				param.setUserId(userId);
				for(GameDao game : list) {
					param.setGameId(game.getGameId());
					game.setReserveYn(gameReserveRedis.findUser(param)!=null?"Y":"N");
					game.setRemaningHour((int)LocalDateTime.now().until(game.getCloseTime(), ChronoUnit.HOURS));
				}
			}
		}
		
		return list;
	}

	public GameReserveRedisDao findReserve(GameReserveRedisDao vo) {
		return gameReserveRedis.findUser(vo);
	} 
	
	public int reserve(GameReserveRedisDao vo) {
		int result = 0;
		if(gameReserveRedis.findUser(vo)!=null) {
				result = -999;
		}else {
			vo.setReserveTime(LocalDateTime.now());
			gameReserveRedis.addReserve(vo);
			GameDao game = gameSelector.find(vo.getGameId());
			if(game!=null) {
				game.addReserveUser(vo.getUserId());
				result = 1;
			}else {
				result = -998;
			}
		}
		return result;
	}
	
	public int cancel(GameReserveRedisDao vo) {
		int result = 0;
		if(gameReserveRedis.findUser(vo)!=null) {
			gameReserveRedis.removeReserve(vo);
			GameDao game = gameSelector.find(vo.getGameId());
			if(game!=null) {
				game.removeReserveUser(vo.getUserId());
				result = 1;
			}else {
				result = -998;
			}
		}else {
			result = 1;
		}
		return result;
	}
	
	public int install(GameInstallRedisDao vo) {
		int result = 0;
		GameDao game = gameSelector.find(vo.getGameId());
		if(game!=null) {
			vo.setInstallTime(LocalDateTime.now());
			gameInstallRedis.addHist(vo);
			game.addInstallUser(vo.getUserId());
			if(game.getInstallRewardHist()==null || game.getInstallRewardHist().get(vo.getUserId())==null){
		    	GameRewardRedisDao grrd = new GameRewardRedisDao();
		    	grrd.setRewardType(RewardType.INSTALL);
		    	grrd.setGameId(vo.getGameId());
		    	grrd.setUserId(vo.getUserId());
		    	grrd.setRewardItem(RewardItem.EMOTICON);
		    	grrd.setMsg("게임설치 보상 ");
		    	gameRewardRedis.addReward(grrd);
		    	
				game.addInstallRewardHist(vo.getUserId());
			}
		}
		result = 1;
		return result;
	}

	public int reserveCnt(String gameId){
		Map<String, GameDao> map = GameTable.getInstance();
		GameDao game = map.get(gameId);
		return game!=null?game.getReserveCnt():-998;
	}

	public long reserveNo(String gameId, String userId){
		Map<String, GameDao> map = GameTable.getInstance();
		GameDao game = map.get(gameId);
		if(game!=null) {
			System.out.println(game.getReserveUser());
			ReserveDao reserve = game.getReserveUser()!=null?game.getReserveUser().get(userId):null;
			return reserve!=null?reserve.getIndex():-999;
		}else {
			return -998;
		}
	}
}
