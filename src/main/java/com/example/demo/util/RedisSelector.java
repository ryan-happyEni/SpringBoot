package com.example.demo.util;

import java.util.Map;
import java.util.Set;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

public class RedisSelector {
	RedisTemplate<String, String> redisTemplate;
	public RedisSelector(RedisTemplate<String, String> redisTemplate) {
		this.redisTemplate=redisTemplate;
	}
	
	public void intSerializer() {
	    StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

	    redisTemplate.setKeySerializer(stringRedisSerializer);
	    redisTemplate.setValueSerializer(stringRedisSerializer);
	    redisTemplate.setHashKeySerializer(stringRedisSerializer);
	    redisTemplate.setHashValueSerializer(stringRedisSerializer);
	}
	
	public void delete(String item) {
	    redisTemplate.delete(item);
	}
	
	public void save(String item, String hash, String id, Map<String, Object> values) {
	    if (values!=null) {
	    	Set<String> keys = values.keySet();
	    	String hashKey=hash+id;
	    	redisTemplate.boundSetOps(item).add(id);
	    	for(String key:keys) {
            	redisTemplate.boundHashOps(hashKey).put(key, values.get(key));
	    	}
	    }
	}
}
