package com.labreport.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.labreport.bean.Comment;
import com.labreport.bean.Praise;
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
	
	public void addTopic(Topic topic) {
		String sql = "insert into topic values(?,?,?,?)";
		try {
			statement = connection.prepareStatement(sql);
			statement.setString(1, topic.getTopicName());
			statement.setString(2, topic.getTitle());
			statement.setInt(3, topic.getLikes());
			statement.setString(4, topic.getPosterName());
			statement.execute();
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void addComment(Comment comment) {
		String sql = "insert into comment values(?,?,?,?)";
		try {
			statement = connection.prepareStatement(sql);
			statement.setString(1, comment.getTopicName());
			statement.setString(2, comment.getComment());
			statement.setString(3, comment.getUserName());
			statement.setInt(4, comment.getLikes());
			statement.execute();
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
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
				String posterName = rs.getString("poster");
				topics.add(new Topic(name,title,likes,posterName));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return topics;
	}
	
	//根据发帖人的用户名获取其对应的帖子
	public ArrayList<Topic> getTopics(String poster){
		ArrayList<Topic> topics = new ArrayList<Topic>();
		String sql = "select * from topic where poster=?";
		try {
			statement = connection.prepareStatement(sql);
			statement.setString(1, poster);
			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
				String title = rs.getString("topicname");
				String content = rs.getString("title");
				int likes = rs.getInt("likes");
				topics.add(new Topic(title,content,likes,poster));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return topics;
	}
	
	public ArrayList<Comment> getComments(String title){
		ArrayList<Comment> comments = new ArrayList<Comment>();
		String comment = null;
		String userName = null;
		int likes = 0;
		String sql = "select * from comment where topicname=?";
		try {
			statement = connection.prepareStatement(sql);
			statement.setString(1, title);
			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
				comment = rs.getString("comment");
				userName = rs.getString("username");
				likes = rs.getInt("likes");
				comments.add(new Comment(title,comment,userName,likes));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return comments;
	}
	
	public ArrayList<Comment> getAllComments() {
		ArrayList comments = new ArrayList<Comment>();
		String topicName = null;
		String comment = null;
		String userName = null;
		int likes = 0;
		String sql = "select * from comment";
		try {
			statement = connection.prepareStatement(sql);
			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
				topicName = rs.getString("topicname");
				comment = rs.getString("comment");
				userName = rs.getString("username");
				likes = rs.getInt("likes");
				comments.add(new Comment(topicName,comment,userName,likes));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return comments;
	}
	
	public ArrayList<Topic> getTopicOrder(){
		ArrayList<Topic> topics = new ArrayList<Topic>();
		String sql = "select * from topic order by likes desc";
		try {
			statement = connection.prepareStatement(sql);
			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
				String title = rs.getString("topicname");
				String content = rs.getString("title");
				int likes = rs.getInt("likes");
				String poster = rs.getString("poster");
				topics.add(new Topic(title,content,likes,poster));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return topics;		
	}
	
	public int getNumOfUsers() {
		int num = 0;
		String sql = "select count(*) from user";
		try {
			statement = connection.prepareStatement(sql);
			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
				num = rs.getInt("count(*)");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return num;
	}
	
	public int getNumOfTopic() {
		int num = 0;
		String sql = "select count(*) from topic";
		try {
			statement = connection.prepareStatement(sql);
			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
				num = rs.getInt("count(*)");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return num;
	}
	
	public int getNumOfComment() {
		int num = 0;
		String sql = "select count(*) from comment";
		try {
			statement = connection.prepareStatement(sql);
			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
				num = rs.getInt("count(*)");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return num;
	}
	
	public void updateTopic(Topic topic) {
		String sql = "update topic set title=? where topicname=?";
		try {
			statement = connection.prepareStatement(sql);
			statement.setString(1, topic.getTitle());
			statement.setString(2, topic.getTopicName());
			statement.execute();
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteTopic(String title) {
		String sql = "delete from topic where topicname=?";
		try {
			statement = connection.prepareStatement(sql);
			statement.setString(1, title);
			statement.execute();
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void addPraise(Praise p) {
		String sql = "insert into praise values(?,?,?)";
		try {
			statement = connection.prepareStatement(sql);
			statement.setString(1, p.getTopicname());
			statement.setString(2, p.getUsername());
			statement.setBoolean(3, p.isPraise());
			statement.execute();
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Praise> getPraise(String username) {
		ArrayList<Praise> praises = new ArrayList<Praise>();
		String sql = "select * from praise where username=? and praise=1";
		String title = null;
		boolean praise = false;
		try {
			statement = connection.prepareStatement(sql);
			statement.setString(1, username);
			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
				title = rs.getString("topicname");
				praise = rs.getBoolean("praise");
				praises.add(new Praise(username, title, praise));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return praises;
	}
	
	public void close() {
		try {
			if(statement!=null) {
				statement.close();
			}
			if(connection!=null) {
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
