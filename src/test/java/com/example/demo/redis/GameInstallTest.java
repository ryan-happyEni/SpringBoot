package com.example.demo.redis;

import java.time.LocalDateTime;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.InheeApplication;
import com.example.demo.redis.repository.GameInstallRedis;
import com.example.demo.redis.repository.dao.GameInstallRedisDao;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = InheeApplication.class)
public class GameInstallTest {

    @Resource(name = "redisTemplate")
    private SetOperations<String, String> setOperations;

    @Resource(name="redisTemplate")
    private ZSetOperations<String, String> zSetOperations;

    @Resource(name = "redisTemplate")
    private ListOperations<String, String> listOperations;

    @Autowired
    GameInstallRedis gameInstallRedis;
    
    @Test
    public void redisTest1() {
    	try {
	    	GameInstallRedisDao dao = new GameInstallRedisDao();
	    	dao.setGameId("Game1");
	    	dao.setUserId("Test41");
	    	dao.setInstallTime(LocalDateTime.now());
	    	gameInstallRedis.addHist(dao);
	    	
	    	dao = new GameInstallRedisDao();
	    	dao.setGameId("Game1");
	    	dao.setUserId("Test2");
	    	dao.setInstallTime(LocalDateTime.now());
	    	gameInstallRedis.addHist(dao);
	    	
	    	dao = new GameInstallRedisDao();
	    	dao.setGameId("Game1");
	    	dao.setUserId("Test3");
	    	dao.setInstallTime(LocalDateTime.now());
	    	gameInstallRedis.addHist(dao);
	    	
	    	dao = new GameInstallRedisDao();
	    	dao.setGameId("Game1");
	    	dao.setUserId("Test2");
	    	dao.setInstallTime(LocalDateTime.now().plusMinutes(10));
	    	gameInstallRedis.addHist(dao);


	        System.out.println("########################");
	        System.out.println("game>"+setOperations.members("game:install"));
	        System.out.println("user>"+zSetOperations.range("game:install:game"+dao.getGameId()+":user", 0, 10));
	        System.out.println("time>"+setOperations.members("game:install:game"+dao.getGameId()+":useri"+dao.getUserId()+":reserveTime"));
	        System.out.println("########################");

	        System.out.println("########################");
	        gameInstallRedis.findUsers(dao, 0, 5, false).forEach(user->{
	        	System.out.println(user);
	        });
	        System.out.println("########################");
	    	dao.setGameId("Game1");
	    	dao.setUserId("Test3");
	    	dao.setPk("3ecd73fc-5ac0-4d7c-b08f-fcc825e92a35");
	        System.out.println("dao>"+gameInstallRedis.findUser(dao));
	        System.out.println("########################");

	        listOperations.rightPush("test:task", "자기소개");
	        listOperations.rightPush("test:task", "취미소개");
	        listOperations.rightPush("test:task", "소망소개");
	        listOperations.rightPush("test:task", "선임이직");
            
            System.out.println(listOperations.range("test:task", 0, 2));
            System.out.println(listOperations.range("test:task", 0, 2));
	        
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
}