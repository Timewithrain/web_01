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
		//����username�����ݿ��л�ȡuser��Ϣ
		UserDAO userdao = new UserDAO();
		User user = userdao.getUser(username);
		//�ж�����������Ƿ���ȷ
		if(password.equals(user.getPassword())) {
			System.out.println("�û���Ϊ��" + username);
			System.out.println("����Ϊ��" + password);
//			writer.println("��½�ɹ���");
//			writer.println("�û���Ϊ��" + username);
//			writer.println("����Ϊ��" + password);
			//��������ת
			request.getSession().setAttribute("username", username);
			request.getSession().setAttribute("password", password);
			request.getRequestDispatcher("success.jsp").forward(request, response);
			
		}else {
//			writer.println("�û������������");
//			request.getRequestDispatcher("failure.jsp").forward(request, response);
			//�ͻ����ض���
			response.sendRedirect("failure.jsp");
		}
		
	}
}
