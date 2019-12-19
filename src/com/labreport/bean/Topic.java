package com.labreport.bean;

public class Topic {
	private String topicName;
	private String title;
	private int likes;
	
	public Topic(String topicName,String title,int likes) {
		this.topicName = topicName;
		this.title = title;
		this.likes = likes;				
	}

	public String getTopicName() {
		return topicName;
	}

	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getLikes() {
		return likes;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}
	
}
