package com.labreport.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.labreport.bean.Topic;
import com.labreport.bean.User;


public class LabReportDAO {
	private Connection connection;
	private PreparedStatement statement;
	
	public LabReportDAO() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/labreport?serverTimezone=UTC","root","105036");
			connection.setAutoCommit(false);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	public User getUser(String name) {
		String avatar = null;
		String OS = null;
		String mark = null;
		String sql = "select * from user where name=?";
		try {
			statement = connection.prepareStatement(sql);
			statement.setString(1, name);
			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
				avatar = rs.getString("avatar");
				OS = rs.getString("OS");
				mark = rs.getString("mark");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new User(name,avatar,OS,mark);
	}
	
	public ArrayList<User> getAllUsers(){
		ArrayList<User> users = new ArrayList<User>();
		String sql = "select * from user";
		try {
			statement = connection.prepareStatement(sql);
			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
				String name = rs.getString("name");
				String avatar = rs.getString("avatar");
				String OS = rs.getString("OS");
				String mark = rs.getString("mark");
				users.add(new User(name,avatar,OS,mark));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return users;
	}
	
	public ArrayList<Topic> getAllTopics(){
		ArrayList<Topic> topics = new ArrayList<Topic>();
		String sql = "select * from topic";
		try {
			statement = connection.prepareStatement(sql);
			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
				String name = rs.getString("topicname");
				String title = rs.getString("title");
				int likes = rs.getInt("likes");
				topics.add(new Topic(name,title,likes));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return topics;
	}
	
	public void close() {
		try {
			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
