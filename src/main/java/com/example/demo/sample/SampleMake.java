package com.example.demo.sample;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.admin.service.AdminGameService;
import com.example.demo.data.RewardItem;
import com.example.demo.data.RewardType;
import com.example.demo.memdb.table.dao.GameDao;
import com.example.demo.redis.repository.GameInstallRedis;
import com.example.demo.redis.repository.GameReserveRedis;
import com.example.demo.redis.repository.GameRewardRedis;
import com.example.demo.redis.repository.dao.GameInstallRedisDao;
import com.example.demo.redis.repository.dao.GameReserveRedisDao;
import com.example.demo.redis.repository.dao.GameRewardRedisDao;

@Component
public class SampleMake {

    @Autowired
    private AdminGameService adminGameService;
    
    @Autowired
    GameReserveRedis gameReserveRedis;
    
    @Autowired
    GameInstallRedis gameInstallRedis;
    
    @Autowired
    GameRewardRedis gameRewardRedis;
    
	public void make(int makeCnt) {
    	try {
    		SecureRandom random = new SecureRandom();
			String strStartTime = "2018-09-01T09:00:00";
			LocalDateTime st = LocalDateTime.parse(strStartTime);
			long start = adminGameService.count()+1;
			long last = start + makeCnt;
    		for(long i=start; i<last; i++) {
    			GameDao vo = new GameDao();
    			vo.initMap();
    			vo.setGameName("Game"+i);
    			vo.setOpenTime(st.plusDays(random.nextInt(60)));
    			vo.setCloseTime(vo.getOpenTime().plusDays(10));
    			vo.setGameId(UUID.randomUUID()+"");
    			vo.setReserveRewardItem(RewardItem.EMOTICON);
    			vo.setInstallRewardItem(RewardItem.GAME_ITEM);
    			vo.setLottoRewardItem(RewardItem.GIFTICON);
    			int reserveCnt = random.nextInt(1000);
    			int installCnt = random.nextInt(1000);

    			for(int u=1; u<reserveCnt;u++) {
    		    	GameReserveRedisDao dao = new GameReserveRedisDao();
    		    	dao.setGameId(vo.getGameId());
    		    	dao.setUserId("test"+u);
    		    	dao.setReserveTime(LocalDateTime.now());
    		    	gameReserveRedis.addReserve(dao);
    				
    				vo.addReserveUser("test"+u);

    				if(LocalDateTime.now().until(vo.getCloseTime(), ChronoUnit.HOURS)<=0) {
        		    	GameRewardRedisDao grrd = new GameRewardRedisDao();
        		    	grrd.setRewardType(RewardType.RESERVE);
        		    	grrd.setGameId(vo.getGameId());
        		    	grrd.setUserId("test"+u);
        		    	grrd.setRewardItem(RewardItem.EMOTICON);
        		    	grrd.setMsg("사전예약 보상");
        		    	gameRewardRedis.addReward(grrd);
        				
        				vo.addReserveRewardHist("test"+u);
    				}
    				
    				if(u==100 || u==500 || u==1000 || u==5000 || u%10000==0) {
        		    	GameRewardRedisDao grrd = new GameRewardRedisDao();
        		    	grrd.setRewardType(RewardType.LOTTO);
        		    	grrd.setGameId(vo.getGameId());
        		    	grrd.setUserId("test"+u);
        		    	grrd.setRewardItem(RewardItem.EMOTICON);
        		    	grrd.setMsg("사전예약 "+u+"번째 참여 보상 ");
        		    	gameRewardRedis.addReward(grrd);
    				}
    			}

    			for(int u=1; u<installCnt;u++) {
    				if(LocalDateTime.now().until(vo.getCloseTime(), ChronoUnit.HOURS)<=0) {
        		    	GameInstallRedisDao dao = new GameInstallRedisDao();
        		    	dao.setGameId(vo.getGameId());
        		    	dao.setUserId("test"+u);
        		    	dao.setInstallTime(LocalDateTime.now());
        		    	gameInstallRedis.addHist(dao);

        				vo.addInstallUser("test"+u);
        				
        				if(u<reserveCnt) {
	        		    	GameRewardRedisDao grrd = new GameRewardRedisDao();
	        		    	grrd.setRewardType(RewardType.INSTALL);
	        		    	grrd.setGameId(vo.getGameId());
	        		    	grrd.setUserId("test"+u);
	        		    	grrd.setRewardItem(RewardItem.EMOTICON);
	        		    	grrd.setMsg("게임설치 보상 ");
	        		    	gameRewardRedis.addReward(grrd);
	        				
	        				vo.addInstallRewardHist("test"+u);
        				}
    				}
    			}

    			adminGameService.insert(vo);
    			System.out.println(LocalDateTime.now());
    		}
    	}catch(Exception e) {
    	}
	}
}
