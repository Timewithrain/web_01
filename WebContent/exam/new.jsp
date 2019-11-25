<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
import="java.util.*,javax.servlet.http.*"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="new.css">
    <link rel="stylesheet" href="jquery-ui.min.css">
    <script src="jquery-1.12.4.js"></script>
    <script src="jquery-ui.js"></script>
    <script src="new.js"></script>
    <title>Document</title>
</head>
<body>
    <div id="header" class="clearfix">
        <div id="logo" class="v-center">
            服务管理网站
            <!-- <img src="" alt=""> -->
        </div>
        <div id="searchbar" class="v-center">
            <input type="search" name="" id="search">
        </div>
        <div id="login" class="v-center">
            <%
                String name = (String)request.getSession().getAttribute("username");
                if(name==null){
            %>
                <a id="register-bnt" href="#">注册</a> |
                <a id="login-bnt" href="#">登陆</a>
            <%
                }else{
            %>
                    <div>您好,<%=name%>! |
                        <a href="../resetpassword.jsp">重置密码</a>
                        |<a href= <%=request.getContextPath() + "/exit" %>>退出</a></div>
            <%
                }
            %>
            
        </div>
    </div>
    <div id="login-dialog">
        <form action="login" method="POST">
            <table>
                <tr>
                    <td>用户名：</td>
                    <td><input type="text" name="username" id="username"></td>
                </tr>
                <tr>
                    <td>密码：</td>
                    <td><input type="password" name="password" id="password"></td>
                </tr>
                <tr>
                    <td><input type="submit" value="登录"></td>
                    <td><input type="button" value="取消" id="login-close"></td>
                </tr>
            </table>
        </form>
    </div>
    <div id="register-dialog">
        <form action="register" method="POST">
            <table>
                <tr>
                    <td>用户名：</td>
                    <td><input type="text" name="username" id="username"></td>
                </tr>
                <tr>
                    <td>密码：</td>
                    <td><input type="password" name="password" id="password"></td>
                </tr>
                <tr>
                    <td>确认密码：</td>
                    <td><input type="password" name="check-password" id="check-password"></td>
                </tr>
                <tr>
                    <td><input type="submit" value="注册"></td>
                    <td><input type="button" value="取消" id="register-close"></td>
                </tr>
            </table>
        </form>
    </div>
    <div id="center" class="clearfix">
        <dic id="apps">
            <div id="app-content">
                <div class="app" title="服务1的简单介绍；介绍服务1的简单功能和使用方法">服务1</div>
                <div class="app" title="服务2的简单介绍；介绍服务2的简单功能和使用方法">服务2</div>
                <div class="app" title="服务3的简单介绍；介绍服务3的简单功能和使用方法">服务3</div>
                <div class="app" title="服务4的简单介绍；介绍服务4的简单功能和使用方法">服务4</div>
            </div>
        </dic>
        <div id="buttons">
            <div id="add" class="button"><a href="#">添加&gt;</a></div>
            <div id="del" class="button"><a href="#">&lt;删除</a></div>
        </div>
        <div id="servers">
            <div id="server-content">
                <div class="server">
                    <div class="checkoption clearfix">
                        <input type="checkbox" id="server1" class="server-check">
                        <div class="servername">服务器1</div>
                    </div>
                    <div id="oriservers1" class="oriservers">
                        <div class="oriserver">原始服务器1.1</div>
                        <div class="oriserver">原始服务器1.2</div>
                    </div>
                </div>
                <div class="server">
                    <div class="checkoption clearfix">
                        <input type="checkbox" id="server2" class="server-check">
                        <div class="servername">服务器2</div>
                    </div>
                    <div id="oriservers2" class="oriservers">
                        <div class="oriserver">原始服务器2.1</div>
                        <div class="oriserver">原始服务器2.2</div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div id="footer" class="clearfix">
        <div id="update-date" class="v-center">
            @ 1997-2019
        </div>
        <div id="copyright" class="v-center">
            公司版权所有
        </div>
        <div id="links" class="clearfix v-center">
            <div class="link"><a href="#">| 公司简介</a></div>
            <div class="link"><a href="#">| 联系方式</a></div>
            <div class="link"><a href="#">| 招聘信息</a></div>
            <div class="link"><a href="#">| 客户服务</a></div>
            <div class="link"><a href="#">| 隐私政策</a></div>
            <div class="link"><a href="#">| 广告服务</a></div>
            <div class="link"><a href="#">| 网站地图</a></div>
            <div class="link"><a href="#">| 不良信息举报</a></div>
        </div>
    </div>
</body>
</html>