package com.watermelon.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.watermelon.bean.User;

public class UserDAO {
	Connection connection;
	PreparedStatement statement;
	
	public UserDAO() {
		//初始化数据库驱动
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			//建数据库连接
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/watermelon?characterEncoding=UTF-8","root","105036");
			connection.setAutoCommit(false);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	//从数据库中查找用户信息
	public User getUser(String username) {
		String name = null;
		String password = null;
		String sql = "select * from user where name=?";
		try {
			statement = connection.prepareStatement(sql);
			statement.setString(1, username);
			ResultSet set = statement.executeQuery();
			if(set.next()) {
				name = set.getString("name");
				password = set.getString("password");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new User(name,password);
	}
	
	//判断数据库中用户是否已经存在
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
	
	//向数据库中添加用户记录
	public void addUser(User user) {
		String sql = "insert into user values (?,?,?)";
		try {
			statement = connection.prepareStatement(sql);
			statement.setString(1, user.getUsername());
			statement.setString(2, user.getPassword());
			statement.setString(3, null);
			statement.execute();
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
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
