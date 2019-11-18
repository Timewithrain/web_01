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
		//���û��Ѿ�����ʱ��������ʾ��Ϣ����ת��ע��ҳ������ע��
		if(userDao.isUserExists(username)) {	
			try {
				writer.println("�û����Ѿ����ڣ�1�����ת��ע��ҳ��");
				request.getRequestDispatcher("register.jsp").forward(request, response);
				Thread.sleep(1000);
			} catch (ServletException | InterruptedException e) {
				e.printStackTrace();
			}
		}else {
			//���ݿ����û�������ʱ�������ݿ���д��ע����Ϣ����ת����½ҳ��
			try {
				userDao.addUser(new User(username,password));
				request.getRequestDispatcher("Welcome.jsp").forward(request, response);
			} catch (ServletException e) {
				e.printStackTrace();
			}
		}
	}
}
