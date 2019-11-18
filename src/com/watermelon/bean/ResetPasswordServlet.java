package com.watermelon.bean;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.watermelon.DAO.UserDAO;

public class ResetPasswordServlet extends HttpServlet {
	public void service(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter writer = response.getWriter();
		String username = (String) request.getSession().getAttribute("username");
		if(username==null) {
			response.sendRedirect("Welcome.jsp");
			return;
		}
		String pwd = request.getParameter("newpassword");
		String newpwd = request.getParameter("check-newpassword");
		UserDAO userDao = new UserDAO();
		if(pwd.equals(newpwd)) {
			userDao.update(new User(username,pwd));
			writer.println("ÃÜÂëÐÞ¸Ä³É¹¦£¡");
		}else {
			request.getRequestDispatcher("resetpassword.jsp").forward(request, response);
		}
	}
}
