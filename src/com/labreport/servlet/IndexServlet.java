package com.labreport.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.labreport.DAO.LabReportDAO;
import com.labreport.bean.Comment;
import com.labreport.bean.Praise;
import com.labreport.bean.Topic;
import com.labreport.bean.User;
import com.mysql.cj.x.protobuf.MysqlxDatatypes.Array;

import net.sf.json.JSONObject;

public class IndexServlet extends HttpServlet {
	public void service(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {
		LabReportDAO labreportDAO = new LabReportDAO();
		String infor = request.getParameter("infor");
		try {
			Method method = getClass().getDeclaredMethod(infor, HttpServletRequest.class,HttpServletResponse.class,LabReportDAO.class);
			method.invoke(this,request,response,labreportDAO);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		labreportDAO.close();
	}
	
	public void getUser(HttpServletRequest request,HttpServletResponse response,LabReportDAO labreportDAO) throws IOException {
		ArrayList<User> users = labreportDAO.getAllUsers();
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
	
	//根据前端传回的title返回对应帖子的评论
	public void getComment(HttpServletRequest request,HttpServletResponse response,LabReportDAO labreportDAO) throws IOException {
		String title = request.getParameter("title");
		ArrayList<Comment> comments = labreportDAO.getComments(title);
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(comments);
		response.setCharacterEncoding("utf-8");
		response.getWriter().println(json);
	}
	
	public void addComment(HttpServletRequest request,HttpServletResponse response,LabReportDAO labreportDAO) throws IOException{
		String title = request.getParameter("title");
		String comment = request.getParameter("comment");
		String username = (String) request.getSession().getAttribute("loginStatus");
		labreportDAO.addComment(new Comment(title,comment,username,0));
		response.getWriter().println("0");
	}
	
	//获取登录信息，若未登录则返回notLogin，若登录则返回登录用户信息
	public void getLoginStatus(HttpServletRequest request,HttpServletResponse response,LabReportDAO labreportDAO) throws IOException {
		String loginStatus = (String) request.getSession().getAttribute("loginStatus");
		response.setCharacterEncoding("UTF-8");
		PrintWriter writer = response.getWriter();
		String str = null;
		if(loginStatus==null) {
			str = "{'infor':'notLogin'}";
		}else {
			str = "{'infor':'"+loginStatus+"'}";
		}
		writer.println(JSONObject.fromObject(str));
	}
	
	//向数据库添加帖子
	public void addPost(HttpServletRequest request,HttpServletResponse response,LabReportDAO labreportDAO) throws ServletException, IOException {
		String titleName = request.getParameter("title");
		String content = request.getParameter("content");
		String poster = (String) request.getSession().getAttribute("loginStatus");
		Topic topic = new Topic(titleName,content,0,poster);
		labreportDAO.addTopic(topic);
		response.getWriter().println("0");
	}
	
	public void getInfor(HttpServletRequest request,HttpServletResponse response,LabReportDAO labreportDAO) throws IOException {
		int userNum = labreportDAO.getNumOfUsers();
		int topicNum = labreportDAO.getNumOfTopic();
		int commentNum = labreportDAO.getNumOfComment();
		String str = "{'userNum':"+userNum+",'topicNum':"+topicNum+",'commentNum':"+commentNum+"}";
		JSONObject json = JSONObject.fromObject(str);
		response.getWriter().println(json);
	}
	
	public void getTopics(HttpServletRequest request,HttpServletResponse response,LabReportDAO labreportDAO) throws JsonProcessingException {
		String poster = (String) request.getSession().getAttribute("loginStatus");
		ArrayList<Topic> topics = labreportDAO.getTopics(poster);
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(topics);
		try {
			response.setCharacterEncoding("utf-8");
			response.getWriter().println(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void updateTopic(HttpServletRequest request,HttpServletResponse response,LabReportDAO labreportDAO) throws IOException {
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		labreportDAO.updateTopic(new Topic(title,content,0));
		response.getWriter().println("0");
	}
	
	public void deleteTopic(HttpServletRequest request,HttpServletResponse response,LabReportDAO labreportDAO) throws IOException {
		String title = request.getParameter("title");
		labreportDAO.deleteTopic(title);
		response.getWriter().println("0");
	}
	
	public void getTopicOrder(HttpServletRequest request,HttpServletResponse response,LabReportDAO labreportDAO) throws IOException {
		ArrayList<Topic> topics = labreportDAO.getTopicOrder();
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(topics);
		response.setCharacterEncoding("utf-8");
		response.getWriter().println(json);
	}
	
	public void addPraise(HttpServletRequest request,HttpServletResponse response,LabReportDAO labreportDAO) throws IOException {
		String name = (String) request.getSession().getAttribute("loginStatus");
		String title = request.getParameter("title");
		labreportDAO.addPraise(new Praise(name, title, true));
		int likes = labreportDAO.getTopic(title).getLikes();
		response.getWriter().println(likes);
	}
	
	public void subPraise(HttpServletRequest request,HttpServletResponse response,LabReportDAO labreportDAO) throws IOException {
		String name = (String) request.getSession().getAttribute("loginStatus");
		String title = request.getParameter("title");
		labreportDAO.subPraise(new Praise(name, title, false));
		int likes = labreportDAO.getTopic(title).getLikes();
		response.getWriter().println(likes);
	}
	
	public void getPraise(HttpServletRequest request,HttpServletResponse response,LabReportDAO labreportDAO) throws IOException{
		String name = (String) request.getSession().getAttribute("loginStatus");
		ArrayList<Praise> praises = labreportDAO.getPraise(name);
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(praises);
		response.setCharacterEncoding("utf-8");
		response.getWriter().println(json);
	}
	
}
