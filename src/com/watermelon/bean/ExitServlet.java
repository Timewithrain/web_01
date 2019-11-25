package com.watermelon.bean;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

public class ExitServlet extends HttpServlet {
	
	public void service(HttpServletRequest request,HttpServletResponse response) {
		HttpSession session = request.getSession();
		session.removeAttribute("username");
		session.removeAttribute("password");
		try {
//			request.getRequestDispatcher("exam/new.jsp").forward(request, response);
			response.sendRedirect("exam/new.jsp");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
