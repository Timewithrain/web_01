package com.watermelon.bean;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.watermelon.DAO.UserDAO;

public class RegisterServlet extends HttpServlet {
	public void service(HttpServletRequest request,HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");;
		PrintWriter writer = response.getWriter();
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String checkPwd = request.getParameter("check-password");
		UserDAO userDao = new UserDAO();
		//当用户已经存在时，返回提示信息并跳转至注册页面重新注册
		if(userDao.isUserExists(username)) {	
			try {
				writer.println("用户名已经存在，1秒后跳转至注册页面");
				request.getRequestDispatcher("register.jsp").forward(request, response);
				Thread.sleep(1000);
			} catch (ServletException | InterruptedException e) {
				e.printStackTrace();
			}
		}else {
			//数据库中用户不存在时，向数据库中写入注册信息并跳转至登陆页面
			try {
				userDao.addUser(new User(username,password));
				request.getRequestDispatcher("Welcome.jsp").forward(request, response);
			} catch (ServletException e) {
				e.printStackTrace();
			}
		}
	}
}
