package com.watermelon.bean;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.sf.json.JSONObject;

public class AjaxServlet extends HttpServlet {
	public void service(HttpServletRequest request,HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("utf-8");
		//纯文本的方式和前端进行数据交互
//		String title = request.getParameter("title");
//		String content = request.getParameter("content");
//		System.out.println(title);
//		System.out.println(content);
//		response.getWriter().print("成功获取请求内容！");
		
		//jackson测试
//		User user = new User("大灰狼","五米长");
//		ObjectMapper mapper = new ObjectMapper(); 
//		String json = mapper.writeValueAsString(user);
//		response.getWriter().print(json);
		
		//JSON的方式和前端进行数据交互
		ObjectMapper mapper = new ObjectMapper();
//		JSONObject jsonStr = JSONObject.fromObject(request.getParameter("data"));
		//获取前端的json字符串并解析为对象
		String jsonStr = request.getParameter("data");
		System.out.println(jsonStr);
		User u = mapper.readValue(jsonStr, User.class);
		//将对象映射为json字符串返回给前端
		String echo = mapper.writeValueAsString(u);
		response.getWriter().print(echo);
		
	}
}
