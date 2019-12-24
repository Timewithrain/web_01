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
		//�����û��Ƿ����
		if(labreportDAO.isUserExists(name)) {
			//���������Ƿ���ȷ
			if(password.equals(user.getPassword())) {
				//���õ�½״̬
				request.getSession().setAttribute("loginStatus", name);
				response.getWriter().println(name);
			}else {
				//�������ʱ���ش�����Ϣ1
				response.getWriter().println("1");
			}
		}else {
			//�û�������ʱ���ش�����Ϣ0
			response.getWriter().println("0");
		}
		
	}
}
