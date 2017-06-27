package com.sist.vo;

import java.sql.Date;

public class Recipe {
	private int id;
	private int user_id;
	private int cat_sub_id;
	private Date regdate;
	private int hit;
	private String title;
	private String summary;
	private int reqmember;
	private String time;
	private String lvl;
	private String img_ori;
	private String img_new;
	private String img;//img_ori와 new중 사용할 이미지 
	
	
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public int getCat_sub_id() {
		return cat_sub_id;
	}
	public void setCat_sub_id(int cat_sub_id) {
		this.cat_sub_id = cat_sub_id;
	}
	public Date getRegdate() {
		return regdate;
	}
	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}
	public int getHit() {
		return hit;
	}
	public void setHit(int hit) {
		this.hit = hit;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}

	public int getReqmember() {
		return reqmember;
	}
	public void setReqmember(int reqmember) {
		this.reqmember = reqmember;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getLvl() {
		return lvl;
	}
	public void setLvl(String lvl) {
		this.lvl = lvl;
	}
	public String getImg_ori() {
		return img_ori;
	}
	public void setImg_ori(String img_ori) {
		this.img_ori = img_ori;
	}
	public String getImg_new() {
		return img_new;
	}
	public void setImg_new(String img_new) {
		this.img_new = img_new;
	}
	
	
}
