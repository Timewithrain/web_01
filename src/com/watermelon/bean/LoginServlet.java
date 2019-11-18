package com.watermelon.bean;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.glass.ui.Application;
import com.watermelon.DAO.UserDAO;

public class LoginServlet extends HttpServlet {
	public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String username = request.getParameter("username");
		String password= request.getParameter("password");
//		PrintWriter writer = response.getWriter();
		//根据username从数据库中获取user信息
		UserDAO userdao = new UserDAO();
		User user = userdao.getUser(username);
		//判断输入的密码是否正确
		if(password.equals(user.getPassword())) {
			System.out.println("用户名为：" + username);
			System.out.println("密码为：" + password);
//			writer.println("登陆成功！");
//			writer.println("用户名为：" + username);
//			writer.println("密码为：" + password);
			//服务器跳转
			request.getSession().setAttribute("username", username);
			request.getSession().setAttribute("password", password);
			request.getRequestDispatcher("success.jsp").forward(request, response);
			
		}else {
//			writer.println("用户名或密码错误！");
//			request.getRequestDispatcher("failure.jsp").forward(request, response);
			//客户端重定向
			response.sendRedirect("failure.jsp");
		}
		
	}
}
