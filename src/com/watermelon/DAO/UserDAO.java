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
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/watermelon?serverTimezone=UTC","root","105036");
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
		String username = user.getUsername();
		String password = user.getPassword();
		//当用户名或密码为空时不做输入
		if(username==null||password==null) {
			return;
		}
		String sql = "insert into user values (?,?,?)";
		try {
			statement = connection.prepareStatement(sql);
			statement.setString(1, username);
			statement.setString(2, password);
			statement.setString(3, null);
			statement.execute();
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//更新用户信息
	public void update(User user) {
		String username = user.getUsername();
		String password = user.getPassword();
		//当用户名或密码为空时不做输入
		if(username==null||password==null) {
			return;
		}
		String sql = "update user set name=?,password=?,email=? where name=?";
		try {
			statement = connection.prepareStatement(sql);
			statement.setString(1, username);
			statement.setString(2, password);
			statement.setString(3, null);
			statement.setString(4, username);
			statement.execute();
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//关闭数据库连接
	public void close() {
		try {
			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
