package com.labreport.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.labreport.DAO.LabReportDAO;
import com.labreport.bean.Comment;
import com.labreport.bean.Topic;
import com.labreport.bean.User;

import net.sf.json.JSONObject;

public class IndexServlet extends HttpServlet {
	public void service(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {
		LabReportDAO labreportDAO = new LabReportDAO();
		String infor = request.getParameter("infor");
		if(infor.equals("getInfor")) {
			getInfor(request, response, labreportDAO);
		}else if(infor.equals("loginStatus")) {
			getLoginStatus(request, response,labreportDAO);
		}else if(infor.equals("user")) {
			getUser(request,response,labreportDAO);
		}else if(infor.equals("comment")) {
			getComment(request, response,labreportDAO);
		}else if(infor.equals("topic")){
			getTopic(request,response,labreportDAO);
		}else if(infor.equals("doPost")) {
			addPost(request, response, labreportDAO);
		}else if(infor.equals("addComment")) {
			addComment(request, response, labreportDAO);
		}else if(infor.equals("updateTopic")) {
			
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
	
	//����avatarΪ�յ��û�,�����䷵����ǰ��
	public ArrayList<User> userFilter(ArrayList<User> users){
		Iterator<User> ite = users.iterator();
		while(ite.hasNext()) {
			User user = (User)ite.next();
			//���߳�ʱʹ�ô˷�ʽ�����׳�ConcurrentModificationException�쳣
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
	
	//����ǰ�˴��ص�title���ض�Ӧ���ӵ�����
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
	
	//��ȡ��¼��Ϣ����δ��¼�򷵻�notLogin������¼�򷵻ص�¼�û���Ϣ
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
	
	//�����ݿ��������
	public void addPost(HttpServletRequest request,HttpServletResponse response,LabReportDAO labreportDAO) throws ServletException, IOException {
		String titleName = request.getParameter("title");
		String content = request.getParameter("content");
		String poster = (String) request.getSession().getAttribute("loginStatus");
		Topic topic = new Topic(titleName,content,0,poster);
		labreportDAO.addTopic(topic);
		request.getRequestDispatcher("index.html").forward(request, response);
	}
	
	public void getInfor(HttpServletRequest request,HttpServletResponse response,LabReportDAO labreportDAO) throws IOException {
		int userNum = labreportDAO.getNumOfUsers();
		int topicNum = labreportDAO.getNumOfTopic();
		int commentNum = labreportDAO.getNumOfComment();
		String str = "{'userNum':"+userNum+",'topicNum':"+topicNum+",'commentNum':"+commentNum+"}";
		JSONObject json = JSONObject.fromObject(str);
//		int[] infor = {userNum,topicNum,commentNum};
//		ObjectMapper mapper = new ObjectMapper();
//		String json = mapper.writeValueAsString(infor);
		response.getWriter().println(json);
	}
	
	public void updateTopic(HttpServletRequest request,HttpServletResponse response,LabReportDAO labreportDAO) {
		String title = request.getParameter("topicname");
		String content = request.getParameter("title");
		labreportDAO.updateTopic(new Topic(title,content,0));
	}
	
}
