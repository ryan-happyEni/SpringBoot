package com.example.demo.web.vo;

import java.util.Date;
import java.util.List;

public class ImageLoaderVo {
	String url;
	Date start_date;
	Date end_date;
	List<ImageVo> imageList;
	String error_code;
	String uuid;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Date getStart_date() {
		return start_date;
	}
	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}
	public Date getEnd_date() {
		return end_date;
	}
	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}
	public List<ImageVo> getImageList() {
		return imageList;
	}
	public void setImageList(List<ImageVo> imageList) {
		this.imageList = imageList;
	}
	public String getError_code() {
		return error_code;
	}
	public void setError_code(String error_code) {
		this.error_code = error_code;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
}
