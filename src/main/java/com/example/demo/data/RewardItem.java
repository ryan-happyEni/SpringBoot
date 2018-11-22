package com.example.demo.data;

public enum RewardItem {
	EMOTICON("이모티콘"),
	GAME_ITEM("게임아이템"),
	GIFTICON("기프티콘"),
	POINT("포인트")
	;
	
	private String title;
	
	RewardItem(String title) {
		this.title = title;
	}
	
	public String getTitle() {
		return title;
	}
}
