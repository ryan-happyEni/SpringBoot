package com.example.demo.redis;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.InheeApplication;
import com.example.demo.redis.repository.GameRewardRedis;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = InheeApplication.class)
public class GameRewardTest {

    @Resource(name = "redisTemplate")
    private SetOperations<String, String> setOperations;

    @Resource(name="redisTemplate")
    private ZSetOperations<String, String> zSetOperations;

    @Autowired
    GameRewardRedis gameRewardRedis;
    
    @Test
    public void redisTest1() {
    	try {
    		/*
	    	GameRewardRedisDao dao = new GameRewardRedisDao();
	    	dao.setRewardType(RewardType.RESERVE);
	    	dao.setGameId("Game1");
	    	dao.setUserId("Test41");
	    	dao.setRewardItem(RewardItem.EMOTICON);
	    	dao.setMsg("사전예약보상금");
	    	gameRewardRedis.addReward(dao);
	    	
	    	dao = new GameRewardRedisDao();
	    	dao.setRewardType(RewardType.RESERVE);
	    	dao.setGameId("Game1");
	    	dao.setUserId("Test2");
	    	dao.setRewardItem(RewardItem.EMOTICON);
	    	dao.setMsg("사전예약보상금");
	    	gameRewardRedis.addReward(dao);
	    	
	    	dao = new GameRewardRedisDao();
	    	dao.setRewardType(RewardType.RESERVE);
	    	dao.setGameId("Game1");
	    	dao.setUserId("Test3");
	    	dao.setRewardItem(RewardItem.EMOTICON);
	    	dao.setMsg("사전예약보상금");
	    	gameRewardRedis.addReward(dao);
	    	
	    	dao = new GameRewardRedisDao();
	    	dao.setRewardType(RewardType.RESERVE);
	    	dao.setGameId("Game2");
	    	dao.setUserId("Test5");
	    	dao.setRewardItem(RewardItem.EMOTICON);
	    	dao.setMsg("사전예약보상금");
	    	gameRewardRedis.addReward(dao);

	        System.out.println("########################");
	        gameRewardRedis.findUsers(dao, 0, 5, false).forEach(user->{
	        	System.out.println(user);
	        });
	        dao.setGameId("");
	        System.out.println("########################");
	        gameRewardRedis.findUsers(dao, 0, 5, true).forEach(user->{
	        	System.out.println(user);
	        });
	        System.out.println("########################");
	    	dao.setGameId("Game1");
	    	dao.setUserId("Test3");
	        System.out.println("dao>"+gameRewardRedis.findUser(dao));
	        System.out.println("########################");

    		*/
	        System.out.println("########################");
	        zSetOperations.incrementScore("test:user:kingbbode:wishxxaa", "경력직 채용", 1);
	        ;
	        System.out.println(zSetOperations.score("test:user:kingbbode:wishxxaa", "경력직 채용"));
	        System.out.println(zSetOperations.range("test:user:kingbbode:wishxxaa", 0, 100));
	        System.out.println("########################");
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
}