package com.labreport.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.labreport.DAO.LabReportDAO;
import com.labreport.bean.Comment;
import com.labreport.bean.Topic;
import com.labreport.bean.User;

public class IndexServlet extends HttpServlet {
	public void service(HttpServletRequest request,HttpServletResponse response) throws IOException {
		LabReportDAO labreportDAO = new LabReportDAO();
		String infor = request.getParameter("infor");
		if(infor.equals("user")) {
			getUser(request,response,labreportDAO);
		}else if(infor.equals("topic")){
			getTopic(request,response,labreportDAO);
		}else if(infor.equals("comment")) {
			getComment(request, response,labreportDAO);
		}
		labreportDAO.close();
	}
	
	public void getUser(HttpServletRequest request,HttpServletResponse response,LabReportDAO labreportDAO) throws IOException {
		ArrayList<User> users = labreportDAO.getAllUsers();
		users = userFilter(users);
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(users);
		response.setCharacterEncoding("utf-8");
		response.getWriter().println(json);
	}
	
	//过滤avatar为空的用户,不将其返回至前端
	public ArrayList<User> userFilter(ArrayList<User> users){
		Iterator<User> ite = users.iterator();
		while(ite.hasNext()) {
			User user = (User)ite.next();
			//单线程时使用此方式避免抛出ConcurrentModificationException异常
			if(user.getAvatar()==null) {
				ite.remove();
			}
		}		
		return users;
	}
	
	public void getTopic(HttpServletRequest request,HttpServletResponse response,LabReportDAO labreportDAO) throws IOException {
		ArrayList<Topic> topics = labreportDAO.getAllTopics();
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(topics);
		response.setCharacterEncoding("utf-8");
		response.getWriter().println(json);
	}
	
	public void getComment(HttpServletRequest request,HttpServletResponse response,LabReportDAO labreportDAO) throws IOException {
		ArrayList<Comment> comments = labreportDAO.getAllComments();
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(comments);
		response.setCharacterEncoding("utf-8");
		response.getWriter().println(json);
	}
	
	//添加帖子
	public void addPost(HttpServletRequest request,HttpServletResponse response,LabReportDAO labreportDAO) {
		String topicName = request.getParameter("topicName");
		String title = request.getParameter("title");
		int likes = Integer.parseInt(request.getParameter("likes"));
		String posterName = (String)request.getSession().getAttribute("LoginStatus");
		Topic topic = new Topic(topicName,title,likes,posterName);
		labreportDAO.addTopic(topic);
		
	}
	
}
