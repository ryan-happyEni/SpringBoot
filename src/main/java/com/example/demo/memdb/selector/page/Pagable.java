package com.example.demo.memdb.selector.page;

import java.util.ArrayList;
import java.util.List;

public class Pagable {

	private String item;
	private int pageNum;
	private int viewCnt;
	private String sortKey;
	private boolean isAsc;
	private List<String> keys;
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public void setSortOrder(String sortKey) {
		this.sortKey = sortKey;
	}
	public String getSortOrder() {
		return this.sortKey;
	}
	public void setKeys(List<String> keys) {
		this.keys = keys;
	}
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public int getViewCnt() {
		return viewCnt;
	}
	public void setViewCnt(int viewCnt) {
		this.viewCnt = viewCnt;
	}
	public void addKey(String key) {
		if(this.keys==null) {
			this.keys=new ArrayList<String>();
		}
		this.keys.add(key);
	}
	public List<String> getKeys(){
		return this.keys;
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
	public String getSortKey() {
		return sortKey;
	}
	public void setSortKey(String sortKey) {
		this.sortKey = sortKey;
	}
	public boolean isAsc() {
		return isAsc;
	}
	public void setAsc(boolean isAsc) {
		this.isAsc = isAsc;
	}

}
