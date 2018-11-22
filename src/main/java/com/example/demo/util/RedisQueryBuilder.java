package com.example.demo.util;

import java.util.List;

import org.springframework.data.redis.connection.SortParameters.Order;
import org.springframework.data.redis.connection.SortParameters.Range;
import org.springframework.data.redis.core.query.SortCriterion;
import org.springframework.data.redis.core.query.SortQuery;
import org.springframework.data.redis.core.query.SortQueryBuilder;

public class RedisQueryBuilder {

	public SortQuery<String> getBuilder(RedisSortOption sortOpt) {
	    Range range = new Range((sortOpt.getPageNum()-1)*sortOpt.getViewCnt(), sortOpt.getViewCnt());

	    SortQueryBuilder<String> qbuilder = SortQueryBuilder.sort(sortOpt.getItem());
	    SortCriterion<String> criterion = null;
	    if(sortOpt.getSortOrder()!=null && !sortOpt.getSortOrder().equals("")) {
	    	criterion = qbuilder.by(sortOpt.getItem()+":*->"+sortOpt.getSortOrder()).order(sortOpt.isAsc()?Order.ASC:Order.DESC);
	    }else {
	    	criterion = qbuilder.noSort();
	    }
	    
	    List<String> keys = sortOpt.getKeys();
	    if(keys!=null) {
	    	for(String key : keys) {
	    	    criterion.get(sortOpt.getItem()+":*->"+key);
	    	}
	    }
	    if(sortOpt.getPageNum()>0) {
	    	criterion.limit(range);
	    }
		return criterion.build();
	}
}
