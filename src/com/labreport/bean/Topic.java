package com.labreport.bean;

public class Topic {
	private String topicName;
	private String title;
	private int likes;
	private String posterName;
	

	public Topic(String topicName,String title,int likes) {
		this.topicName = topicName;
		this.title = title;
		this.likes = likes;				
	}
	
	public Topic(String topicName, String title, int likes, String posterName) {
		super();
		this.topicName = topicName;
		this.title = title;
		this.likes = likes;
		this.posterName = posterName;
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
	
	public String getPosterName() {
		return posterName;
	}

	public void setPosterName(String posterName) {
		this.posterName = posterName;
	}
	
}
