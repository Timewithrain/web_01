package com.watermelon.bean;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HelloServlet extends HttpServlet {
	
	public void service(HttpServletRequest request,HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		try {
			PrintWriter writer = response.getWriter();
			writer.println("Hello,there!");
			writer.println("<br>现在时间为：" + new Date().toLocaleString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
