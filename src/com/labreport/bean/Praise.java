package com.labreport.bean;

public class Praise {
	private String username;
	private String topicname;
	private boolean praise;
	
	public Praise(String username, String topicname, boolean praise) {
		super();
		this.username = username;
		this.topicname = topicname;
		this.praise = praise;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTopicname() {
		return topicname;
	}

	public void setTopicname(String topicname) {
		this.topicname = topicname;
	}

	public boolean isPraise() {
		return praise;
	}

	public void setPraise(boolean praise) {
		this.praise = praise;
	}
	
}
