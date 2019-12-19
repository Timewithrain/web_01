package com.labreport.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.labreport.DAO.LabReportDAO;
import com.labreport.bean.Topic;
import com.labreport.bean.User;

public class IndexServlet extends HttpServlet {
	public void service(HttpServletRequest request,HttpServletResponse response) throws IOException {
		String infor = request.getParameter("infor");
		if(infor.equals("user")) {
			getUser(request,response);
		}else if(infor.equals("topic")){
			getTopic(request,response);
		}
	}
	
	public void getUser(HttpServletRequest request,HttpServletResponse response) throws IOException {
		LabReportDAO labreportDAO = new LabReportDAO();
		ArrayList<User> users = labreportDAO.getAllUsers();
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(users);
		response.setCharacterEncoding("utf-8");
		response.getWriter().println(json);
	}
	
	public void getTopic(HttpServletRequest request,HttpServletResponse response) throws IOException {
		LabReportDAO labreportDAO = new LabReportDAO();
		ArrayList<Topic> topics = labreportDAO.getAllTopics();
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(topics);
		response.setCharacterEncoding("utf-8");
		response.getWriter().println(json);
	}
}
