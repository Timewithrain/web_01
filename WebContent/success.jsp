<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" 
import="java.util.*,javax.servlet.http.*"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>success</title>
</head>
<body>
    <div>success!</div>
    <div>用户名：<%=request.getSession().getAttribute("username")%></div>
    <div>密码：<%=request.getSession().getAttribute("password")%></div>
</body>
</html>