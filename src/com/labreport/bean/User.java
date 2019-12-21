package com.labreport.bean;

public class User {
	private String name;
	private String password;
	private String avatar;
	private String OS;
	private String mark;
	private String email;

	public User() {}
	
	public User(String name, String avatar, String OS, String mark) {
		super();
		this.name = name;
		this.avatar = avatar;
		this.OS = OS;
		this.mark = mark;
	}

	public User(String name, String password, String avatar, String OS, String mark, String email) {
		super();
		this.name = name;
		this.password = password;
		this.avatar = avatar;
		this.OS = OS;
		this.mark = mark;
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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
