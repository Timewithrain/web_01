package com.labreport.bean;

public class Comment {
	private String topicName;
	private String comment;
	private String userName;
	private int likes;
	
	public Comment(String topicName, String comment, String userName) {
		super();
		this.topicName = topicName;
		this.comment = comment;
		this.userName = userName;
	}
	
	public Comment(String topicName, String comment, String userName, int likes) {
		super();
		this.topicName = topicName;
		this.comment = comment;
		this.userName = userName;
		this.likes = likes;
	}



	public String getTopicName() {
		return topicName;
	}
	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getLikes() {
		return likes;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}
	
}
