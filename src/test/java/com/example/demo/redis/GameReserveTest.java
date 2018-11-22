package com.example.demo.redis;

import java.time.LocalDateTime;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.InheeApplication;
import com.example.demo.redis.repository.GameReserveRedis;
import com.example.demo.redis.repository.dao.GameReserveRedisDao;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = InheeApplication.class)
public class GameReserveTest {

    @Resource(name = "redisTemplate")
    private SetOperations<String, String> setOperations;

    @Resource(name="redisTemplate")
    private ZSetOperations<String, String> zSetOperations;

    @Autowired
    GameReserveRedis gameReserveRedis;
    
    @Test
    public void redisTest1() {
    	try {
	    	GameReserveRedisDao dao = new GameReserveRedisDao();
	    	dao.setGameId("Game1");
	    	dao.setUserId("Test41");
	    	dao.setReserveTime(LocalDateTime.now());
	    	gameReserveRedis.addReserve(dao);
	    	
	    	dao = new GameReserveRedisDao();
	    	dao.setGameId("Game1");
	    	dao.setUserId("Test2");
	    	dao.setReserveTime(LocalDateTime.now());
	    	gameReserveRedis.addReserve(dao);
	    	
	    	dao = new GameReserveRedisDao();
	    	dao.setGameId("Game1");
	    	dao.setUserId("Test3");
	    	dao.setReserveTime(LocalDateTime.now());
	    	gameReserveRedis.addReserve(dao);
	
	        
	        System.out.println("########################");
	        System.out.println("game>"+setOperations.members("game:reserve"));
	        System.out.println("user>"+zSetOperations.range("game:reserve:game"+dao.getGameId()+":user", 0, 2));
	        System.out.println("time>"+setOperations.members("game:reserve:game"+dao.getGameId()+":useri"+dao.getUserId()+":reserveTime"));
	    	dao.setGameId("Game1");
	    	dao.setUserId("Test3");
	        //System.out.println("users>"+gameReserveRedis.findUsers(dao, 0, 10));

	        System.out.println("########################");
	        gameReserveRedis.findUsers(dao, 0, 10, false).forEach(user->{
	        	System.out.println(user);
	        });
	        System.out.println("########################");
	    	dao.setGameId("Game1");
	    	dao.setUserId("Test3");
	    	gameReserveRedis.removeReserve(dao);
	        gameReserveRedis.findUsers(dao, 0, 10, true).forEach(user->{
	        	System.out.println(user);
	        });
	        System.out.println("########################");
	        System.out.println("dao>"+gameReserveRedis.findUser(dao));
	        System.out.println("########################");
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
}