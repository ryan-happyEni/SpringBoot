package com.example.demo.memdb.table.dao;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import com.example.demo.data.RewardItem;
import com.fasterxml.jackson.annotation.JsonFormat;

public class GameDao {
	String gameId;
	String gameName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime openTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime closeTime;
	private RewardItem reserveRewardItem; 
	private RewardItem installRewardItem; 
	private RewardItem lottoRewardItem;
	private Map<String, ReserveDao> reserveUser;
	private Map<String, InstallDao> installUser;
	private Map<String, LocalDateTime> reserveRewardHist;
	private Map<String, LocalDateTime> installRewardHist;
	private int pageNum;
	private int viewCnt;
	private int totalCnt;
	private int totalPage;
	private int installCnt;
	private int reserveCnt;
	private String reserveYn;
	private int remaningDay;
	private int remaningHour;
	private long reserveIdx;
	public void initMap() {
		this.reserveUser = new LinkedHashMap<String, ReserveDao>();
		this.installUser = new LinkedHashMap<String, InstallDao>();
		this.reserveRewardHist = new LinkedHashMap<String, LocalDateTime>();
		this.installRewardHist = new LinkedHashMap<String, LocalDateTime>();
	}
	public String getGameId() {
		return gameId;
	}
	public void setGameId(String gameId) {
		this.gameId = gameId;
	}
	public String getGameName() {
		return gameName;
	}
	public void setGameName(String gameName) {
		this.gameName = gameName;
	}
	public LocalDateTime getOpenTime() {
		return openTime;
	}
	public void setOpenTime(LocalDateTime openTime) {
		this.openTime = openTime;
	}
	public LocalDateTime getCloseTime() {
		return closeTime;
	}
	public void setCloseTime(LocalDateTime closeTime) {
		this.closeTime = closeTime;
	}
	public RewardItem getReserveRewardItem() {
		return reserveRewardItem;
	}
	public void setReserveRewardItem(RewardItem reserveRewardItem) {
		this.reserveRewardItem = reserveRewardItem;
	}
	public RewardItem getInstallRewardItem() {
		return installRewardItem;
	}
	public void setInstallRewardItem(RewardItem installRewardItem) {
		this.installRewardItem = installRewardItem;
	}
	public RewardItem getLottoRewardItem() {
		return lottoRewardItem;
	}
	public void setLottoRewardItem(RewardItem lottoRewardItem) {
		this.lottoRewardItem = lottoRewardItem;
	}
	synchronized public void addReserveUser(String userId) {
		ReserveDao user = new ReserveDao();
		user.setIndex(this.reserveIdx++);
		user.setUserId(userId);
		user.setGameId(this.gameId);
		user.setReserveTime(LocalDateTime.now());
		this.reserveUser.put(user.getUserId(), user);
		this.reserveCnt=this.reserveUser.size();
	}
	synchronized public void removeReserveUser(String userId) {
		if(this.reserveUser!=null && this.reserveUser.get(userId)!=null) {
			this.reserveUser.remove(userId);
			this.reserveCnt=this.reserveUser.size();
		}
	}
	public Map<String, ReserveDao> getReserveUser() {
		return this.reserveUser;
	}
	public int getReserveCnt() {
		return this.reserveCnt;
	}
	synchronized public void addInstallUser(String userId) {
		InstallDao user = new InstallDao();
		user.setGameId(this.gameId);
		user.setInstallTime(LocalDateTime.now());
		user.setUserId(userId);
		
		this.installUser.put(user.getUserId()+this.installCnt, user);
		this.installCnt=this.installUser.size();
	}
	public Map<String, InstallDao> getInstallUser() {
		return this.installUser;
	}
	public int getInstallCnt() {
		return this.installCnt;
	}
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public int getTotalCnt() {
		return totalCnt;
	}
	public void setTotalCnt(int totalCnt) {
		this.totalCnt = totalCnt;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getViewCnt() {
		return viewCnt;
	}
	public void setViewCnt(int viewCnt) {
		this.viewCnt = viewCnt;
	}
	public String getReserveYn() {
		return reserveYn;
	}
	public void setReserveYn(String reserveYn) {
		this.reserveYn = reserveYn;
	}
	public int getRemaningDay() {
		return remaningDay;
	}
	public void setRemaningDay(int remaningDay) {
		this.remaningDay = remaningDay;
	}
	public int getRemaningHour() {
		return remaningHour;
	}
	public void setRemaningHour(int remaningHour) {
		this.remaningHour = remaningHour;
	}
	public Map<String, LocalDateTime> getReserveRewardHist() {
		return reserveRewardHist;
	}
	synchronized public void addReserveRewardHist(String userId) {
		this.reserveRewardHist.put(userId, LocalDateTime.now());
	}
	public Map<String, LocalDateTime> getInstallRewardHist() {
		return installRewardHist;
	}
	synchronized public void addInstallRewardHist(String userId) {
		this.installRewardHist.put(userId, LocalDateTime.now());
	}
	public long getReserveIdx() {
		return reserveIdx;
	}
	public void setReserveIdx(long reserveIdx) {
		this.reserveIdx = reserveIdx;
	}
}
