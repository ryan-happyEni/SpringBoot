package com.example.demo.memdb.table.dao;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class InstallDao {
	String gameId;
	String userId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime installTime;
	public String getGameId() {
		return gameId;
	}
	public void setGameId(String gameId) {
		this.gameId = gameId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public LocalDateTime getInstallTime() {
		return installTime;
	}
	public void setInstallTime(LocalDateTime installTime) {
		this.installTime = installTime;
	}
}
