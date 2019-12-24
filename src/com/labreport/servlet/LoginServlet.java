package com.labreport.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.labreport.DAO.LabReportDAO;
import com.labreport.bean.User;

public class LoginServlet extends HttpServlet {
	public void service(HttpServletRequest request,HttpServletResponse response) throws IOException {
		String name = request.getParameter("username");
		String password = request.getParameter("password");
		LabReportDAO labreportDAO = new LabReportDAO();
		User user = labreportDAO.getUser(name);
		response.setCharacterEncoding("utf-8");
		//检验用户是否存在
		if(labreportDAO.isUserExists(name)) {
			//检验密码是否正确
			if(password.equals(user.getPassword())) {
				//设置登陆状态
				request.getSession().setAttribute("loginStatus", name);
				response.getWriter().println(name);
			}else {
				//密码错误时返回错误信息1
				response.getWriter().println("1");
			}
		}else {
			//用户不存在时返回错误信息0
			response.getWriter().println("0");
		}
		
	}
}
