package com.example.demo.memdb.selector;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.springframework.stereotype.Component;

import com.example.demo.memdb.selector.page.Pagable;
import com.example.demo.memdb.table.GameTable;
import com.example.demo.memdb.table.dao.GameDao;

@Component
public class GameMemSelector extends Selector {

	public static int count() {
		Map<String, GameDao> map = GameTable.getInstance();
		return map.keySet().size();
	}
	
	public List<GameDao> findAll() {
		Map<String, GameDao> map = GameTable.getInstance();
		Set<String> keys = map.keySet();
		
		List<GameDao> list = new ArrayList<GameDao>();
		for(String key:keys) {
			list.add(map.get(key));
		}
		return list;
	}
	
	public List<GameDao> findAll(Pagable pagable) {
		Map<String, GameDao> map = GameTable.getInstance();
		Set<String> keys = map.keySet();
		
		List<GameDao> list = new ArrayList<GameDao>();
		int start = pagable.getPageNum()*pagable.getViewCnt();
		int end = start + pagable.getViewCnt();
		if(end>keys.size()) {
			end = keys.size();
		}
		
		Object[] kobj = keys.toArray();
		for(int i=start; i<end; i++) {
			list.add(map.get(kobj[i].toString()));
		}
		return list;
	}
	
	public List<GameDao> getList(Map<String, GameDao> map, int pageNum, int viewCnt){
		Set<String> keys = map.keySet();
		
		List<GameDao> list = new ArrayList<GameDao>();
		int start = pageNum*viewCnt;
		int end = start + viewCnt;
		if(end>keys.size()) {
			end = keys.size();
		}
		
		Object[] kobj = keys.toArray();
		for(int i=start; i<end; i++) {
			list.add(map.get(kobj[i].toString()));
		}
		return list;
	}
	
	public List<GameDao> getList(Map<String, GameDao> map, SortedMap<?, String> sortedMap, boolean isAsc, int pageNum, int viewCnt){
		List<GameDao> list = new ArrayList<GameDao>();
		int currentPage = pageNum-1;
		if(currentPage<0) {
			currentPage=0;
		}
		int start = currentPage*viewCnt;
		int end = start + viewCnt;
		if(sortedMap!=null) {
			int max = sortedMap.size();
			if(end>max) {
				end = max;
			}
			Set<?> sortedKeys = sortedMap.keySet();
			if(sortedKeys!=null && !sortedKeys.isEmpty()) {
				Object[] kobj = sortedKeys.toArray();
				if(isAsc) {
					for(int i=start; i<end; i++) {
						list.add(map.get(sortedMap.get(kobj[i])));
					}
				}else {
					int last = sortedKeys.size()-1;
					start = last - (currentPage*viewCnt);
					end = start - viewCnt+1;
					if(end<1) {
						end = 0;
					}
					
					for(int i=start; i>=end; i--) {
						//list.add(map.get(sortedMap.get(Long.parseLong(kobj[i].toString()))));
						list.add(map.get(sortedMap.get(kobj[i])));
					}
				}
			}
		}else {
			Set<String> keys = map.keySet();
			int max = keys.size();
			if(end>max) {
				end = max;
			}
			Object[] kobj = keys.toArray();
			for(int i=start; i<end; i++) {
				list.add(map.get(kobj[i]));
			}
		}
		return list;
	}
	
	public GameDao find(String id) {
		return GameTable.get(id);
	}

	public int insert(GameDao vo, String id) {
		return GameTable.insert(vo, id);
	}

	public int update(GameDao vo, String id) {
		return GameTable.update(vo, id);
	}

	public int delete(String id) {
		return GameTable.delete(id);
	}
	
	public SortedMap<?, String> getSortedMap(String sortKey, Set<String> filterdKeys) {

		Map<String, GameDao> map = GameTable.getInstance();
		
		Set<String> keys = null;
		if(filterdKeys!=null) {
			keys = filterdKeys;
		}else {
			keys = map.keySet();
		}
		int idx=0;
		if(sortKey.equals("reserveCnt")) {
			TreeMap<Long, String> sortMap = new TreeMap<Long, String>();
			for(String key : keys) {
				sortMap.put(Long.valueOf(""+map.get(key).getReserveCnt()+idx), key);
				idx++;
			}

			return Collections.synchronizedSortedMap(sortMap); 
		}else if(sortKey.equals("installCnt")) {
			TreeMap<Long, String> sortMap = new TreeMap<Long, String>();
			for(String key : keys) {
				sortMap.put(Long.valueOf(""+map.get(key).getInstallCnt()+idx), key);
				idx++;
			}

			return Collections.synchronizedSortedMap(sortMap); 
		}else if(sortKey.equals("closeTime")) {
			TreeMap<Long, String> sortMap = new TreeMap<Long, String>();
			LocalDateTime st = LocalDateTime.parse("9999-12-31T00:00:00");
			for(String key : keys) {
				LocalDateTime time = map.get(key).getCloseTime();
				if(time!=null) {
					sortMap.put(time.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()+idx, key);
				}else {
					sortMap.put(st.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()+idx, key);
				}
				idx++;
			}

			return Collections.synchronizedSortedMap(sortMap); 
		}else if(sortKey.equals("openTime")) {
			TreeMap<Long, String> sortMap = new TreeMap<Long, String>();
			LocalDateTime st = LocalDateTime.parse("9999-12-31T00:00:00");
			for(String key : keys) {
				LocalDateTime time = map.get(key).getOpenTime();
				if(time!=null) {
					sortMap.put(time.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()+idx, key);
				}else {
					sortMap.put(st.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()+idx, key);
				}
				idx++;
			}

			return Collections.synchronizedSortedMap(sortMap); 
		}else if(sortKey.equals("gameName")) {
			TreeMap<String, String> sortMap = new TreeMap<String, String>();
			for(String key : keys) {
				sortMap.put(map.get(key).getGameName()+idx, key);
				idx++;
			}

			return Collections.synchronizedSortedMap(sortMap); 
		}else {
			return null;
		}
	}
}
