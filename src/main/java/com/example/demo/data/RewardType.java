package com.example.demo.data;

public enum RewardType {
	RESERVE("사전예약"),
	INSTALL("게임설치"),
	LOTTO("로또")
	;
	
	private String title;
	
	RewardType(String title) {
		this.title = title;
	}
	
	public String getTitle() {
		return title;
	}
}
