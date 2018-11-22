package com.example.demo.redis;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.ConvertingCursor;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.InheeApplication;
import com.example.demo.redis.repository.GameReserveRedis;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = InheeApplication.class)
public class SpringBootRedisApplicationTests2 {

    @Resource(name="redisTemplate")
	private RedisTemplate<String, String> redisTemplate;

    @Resource(name="redisTemplate")
    private ValueOperations<String, String> valueOperations;

    @Resource(name = "redisTemplate")
    private ListOperations<String, String> listOperations;

    @Resource(name = "redisTemplate")
    private HashOperations<String, String, String> hashOperations;

    @Resource(name = "redisTemplate")
    private SetOperations<String, String> setOperations;

    @Resource(name="redisTemplate")
    private ZSetOperations<String, String> zSetOperations;

    
    @Test
    public void redisTest1() {
    	String gameId="game1";
    	String key = "game:reserve";
    	setOperations.add(key, "Game"+1);
    	setOperations.add(key, "Game"+2);
    	//gameReserveRedis.addGame(gameId, userId);
        hashOperations.put(key+":"+gameId, "gameId", gameId);
        for(int i=1; i<10; i++) {
        	setOperations.add(key+":"+gameId+":user", "Test"+i);
        }
        
        System.out.println("########################");
        System.out.println("game>"+setOperations.members("game:reserve"));
        //System.out.println(hashOperations.entries("game:reserve"));
        System.out.println("user>"+setOperations.members(key+":"+gameId+":user"));
        System.out.println("contains>"+setOperations.isMember(key+":"+gameId+":user", "Testx2"));
        System.out.println("remove>"+setOperations.remove(key+":"+gameId+":user", "Test1"));
        System.out.println("user>"+setOperations.members(key+":"+gameId+":user"));
        //System.out.println("pop>"+setOperations.pop(key+":"+gameId+":user", 2));
        System.out.println("user>"+setOperations.members(key+":"+gameId+":user"));
        
        
        ScanOptions options = ScanOptions.scanOptions().match(key+":"+gameId+":user").count(3).build();
        RedisSerializer<String> redisSerializer = (RedisSerializer<String>) redisTemplate.getKeySerializer();
        Cursor c = (Cursor)redisTemplate.executeWithStickyConnection(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                return new ConvertingCursor<>(redisConnection.scan(options), redisSerializer::deserialize);
            }
        });
        System.out.println(c);
        while (c.hasNext()) {
        	System.out.println("scan>"+(new String((byte[]) c.next())));
        }
        System.out.println("########################");

        //setOperations.add("test:user:kingbbode:hobby", "aaa");
        //setOperations.add("test:user:kingbbode:hobby", "aaa");
        //setOperations.add("test:user:kingbbode:hobby", "ccc");
        //zset
        zSetOperations.add("test:user:kingbbode:wish", "aa", 1);
        zSetOperations.add("test:user:kingbbode:wish", "aa", 2);
        zSetOperations.add("test:user:kingbbode:wish", "cc", 3);
        

        System.out.println(zSetOperations.range("test:user:kingbbode:wish", 0, 10));
        System.out.println("########################");
    }
}