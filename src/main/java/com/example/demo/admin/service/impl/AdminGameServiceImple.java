package com.example.demo.admin.service.impl;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.admin.service.AdminGameService;
import com.example.demo.memdb.selector.GameMemSelector;
import com.example.demo.memdb.table.GameTable;
import com.example.demo.memdb.table.dao.GameDao;

@Service(value="AdminGameService")
public class AdminGameServiceImple implements AdminGameService{

	@Autowired
	private GameMemSelector gameSelector;

	public List<GameDao> findAll(GameDao vo, String orderKey, boolean isAsc){

		Map<String, GameDao> map = GameTable.getInstance();

		List<GameDao> list = gameSelector.getList(map, gameSelector.getSortedMap(orderKey, null), isAsc, vo.getPageNum(), vo.getViewCnt());
		if(list!=null && list.size()>0) {
			GameDao dvo = list.get(0);
			dvo.setPageNum(vo.getPageNum());
			dvo.setTotalCnt(map.keySet().size());
			dvo.setTotalPage(gameSelector.getTotalPage(vo.getViewCnt(), dvo.getTotalCnt()));
		}
		
		return list;
	}

	public List<GameDao> findAllOpendGame(GameDao vo, String orderKey, boolean isAsc){

		Map<String, GameDao> map = GameTable.getInstance();
		Set<String> keys = map.keySet();
		Set<String> filterdKey = new HashSet<String>();
		for(String key : keys) {
			LocalDateTime time = map.get(key).getCloseTime();
			if(time!=null) {
				if(LocalDateTime.now().until(map.get(key).getCloseTime(), ChronoUnit.HOURS)>0) {
					filterdKey.add(key);
				}
			}
		}

		List<GameDao> list = gameSelector.getList(map, gameSelector.getSortedMap(orderKey, filterdKey), isAsc, vo.getPageNum(), vo.getViewCnt());
		if(list!=null && list.size()>0) {
			GameDao dvo = list.get(0);
			dvo.setPageNum(vo.getPageNum());
			dvo.setTotalCnt(filterdKey.size());
			dvo.setTotalPage(gameSelector.getTotalPage(vo.getViewCnt(), dvo.getTotalCnt()));
		}
		
		return list;
	}


	public List<GameDao> findAllClosedGame(GameDao vo, String orderKey, boolean isAsc){

		Map<String, GameDao> map = GameTable.getInstance();
		Set<String> keys = map.keySet();
		Set<String> filterdKey = new HashSet<String>();
		for(String key : keys) {
			LocalDateTime time = map.get(key).getCloseTime();
			if(time!=null) {
				if(LocalDateTime.now().until(map.get(key).getCloseTime(), ChronoUnit.HOURS)<=0) {
					filterdKey.add(key);
				}
			}
		}

		List<GameDao> list = gameSelector.getList(map, gameSelector.getSortedMap(orderKey, filterdKey), isAsc, vo.getPageNum(), vo.getViewCnt());
		if(list!=null && list.size()>0) {
			GameDao dvo = list.get(0);
			dvo.setPageNum(vo.getPageNum());
			dvo.setTotalCnt(filterdKey.size());
			dvo.setTotalPage(gameSelector.getTotalPage(vo.getViewCnt(), dvo.getTotalCnt()));
		}
		
		return list;
	}

	public GameDao find(String game_id) {
		return gameSelector.find(game_id);
	}
	
	public void insert(GameDao vo) {
		gameSelector.insert(vo, vo.getGameId());
	}
	
	public void update(GameDao vo) {
		GameDao org = gameSelector.find(vo.getGameId());
		org.setGameName(vo.getGameName());
		org.setOpenTime(vo.getOpenTime());
		org.setCloseTime(vo.getCloseTime());
		org.setReserveRewardItem(vo.getReserveRewardItem());
		org.setInstallRewardItem(vo.getInstallRewardItem());
		org.setLottoRewardItem(vo.getLottoRewardItem());
		gameSelector.update(org, org.getGameId());
	}
	
	public void delete(GameDao vo) {
		gameSelector.delete(vo.getGameId());
	}
	public int count() {
		return gameSelector.count();
	}
}
