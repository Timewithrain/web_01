package com.labreport.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.labreport.DAO.LabReportDAO;
import com.labreport.bean.User;

public class RegisterServlet extends HttpServlet {
	public void service(HttpServletRequest request,HttpServletResponse response) throws IOException {
		String name = request.getParameter("username");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String checkPwd = request.getParameter("check-password");
		if(checkPwd.equals(password)) {
			User user = new User();
			user.setName(name);
			user.setPassword(password);
			user.setEmail(email);
			LabReportDAO labreportDAO = new LabReportDAO();
			labreportDAO.addUser(user);
			response.getWriter().println("OK");
		}else {
			//当密码与验证密码不匹配时返回错误信息
			response.getWriter().println("0");
		}
	}
}
