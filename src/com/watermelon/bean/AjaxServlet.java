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
		//���ı��ķ�ʽ��ǰ�˽������ݽ���
//		String title = request.getParameter("title");
//		String content = request.getParameter("content");
//		System.out.println(title);
//		System.out.println(content);
//		response.getWriter().print("�ɹ���ȡ�������ݣ�");
		
		//jackson����
//		User user = new User("�����","���׳�");
//		ObjectMapper mapper = new ObjectMapper(); 
//		String json = mapper.writeValueAsString(user);
//		response.getWriter().print(json);
		
		//JSON�ķ�ʽ��ǰ�˽������ݽ���
		ObjectMapper mapper = new ObjectMapper();
//		JSONObject jsonStr = JSONObject.fromObject(request.getParameter("data"));
		//��ȡǰ�˵�json�ַ���������Ϊ����
		String jsonStr = request.getParameter("data");
		System.out.println(jsonStr);
		User u = mapper.readValue(jsonStr, User.class);
		//������ӳ��Ϊjson�ַ������ظ�ǰ��
		String echo = mapper.writeValueAsString(u);
		response.getWriter().print(echo);
		
	}
}
