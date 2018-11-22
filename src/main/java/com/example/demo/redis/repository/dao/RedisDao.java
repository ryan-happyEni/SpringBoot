package com.example.demo.redis.repository.dao;

import lombok.Data;

@Data
public class RedisDao {
    private int pageNum;
    private long totalCnt;
    private int totalPage;
    private int viewCnt;
    
    public long getStartIndex() {
    	long start=0;
    	if(pageNum<2) {
    		start=0;
    	}else {
    		start=(pageNum-1)*viewCnt;
    	}
    	
    	return start;
    }

    public long getEndIndex() {
    	return getStartIndex()+viewCnt-1;
    }
	
	public int getTotalPage(long totalCnt) {
		if(totalCnt<1) {
			return 1;
		}else if(totalCnt%viewCnt==0) {
			return (int)(totalCnt/viewCnt);
		}else {
			return (int)(totalCnt/viewCnt)+1;
		}
	}
}
