package com.labreport.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.labreport.bean.Comment;
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
	
	public void addUser(User user) {
		String username = user.getName();
		String password = user.getPassword();
		if(username==null||password==null) {
			return;
		}
		String sql = "insert into user values(?,?,?,?,?,?)";
		try {
			statement = connection.prepareStatement(sql);
			statement.setString(1, user.getName());
			statement.setString(2, user.getAvatar());
			statement.setString(3, user.getOS());
			statement.setString(4, user.getMark());
			statement.setString(5, user.getPassword());
			statement.setString(6, user.getEmail());
			statement.execute();
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean isUserExists(String username) {
		boolean isExist = false;
		int count = 0;
		String sql = "select count(*) from user where name=?";
		try {
			statement = connection.prepareStatement(sql);
			statement.setString(1, username);
			ResultSet set = statement.executeQuery();
			if(set.next()) {
				count = Integer.parseInt(set.getString("count(*)"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(count!=0) {
			isExist = true;
		}
		return isExist;
	}
	
	public User getUser(String name) {
		String avatar = null;
		String OS = null;
		String mark = null;
		String password = null;
		String email = null;
		String sql = "select * from user where name=?";
		try {
			statement = connection.prepareStatement(sql);
			statement.setString(1, name);
			ResultSet rs = statement.executeQuery();
			//当数据库没有对应用户名的用户时返回空
			while(rs.next()) {
				avatar = rs.getString("avatar");
				OS = rs.getString("OS");
				mark = rs.getString("mark");
				password = rs.getString("password");
				email= rs.getString("email");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new User(name,password,avatar,OS,mark,email);
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
	
	public Comment getComment() {
		String topicName;
		String comment;
		String userName;
		
		return new Comment(topicName, comment, userName);
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
