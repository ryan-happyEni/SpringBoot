package com.example.demo.memdb.selector;

public class Selector {
	public int getTotalPage(int viewCnt, long totalCnt) {
		if(totalCnt<1) {
			return 1;
		}else if(totalCnt%viewCnt==0) {
			return (int)(totalCnt/viewCnt);
		}else {
			return (int)(totalCnt/viewCnt)+1;
		}
	}
}
