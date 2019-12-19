package com.labreport.bean;

public class User {
	private String name;
	private String avatar;
	private String OS;
	private String mark;

	public User(String name, String avatar, String OS, String mark) {
		super();
		this.name = name;
		this.avatar = avatar;
		this.OS = OS;
		this.mark = mark;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getOS() {
		return OS;
	}

	public void setOS(String OS) {
		this.OS = OS;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}
	
}
