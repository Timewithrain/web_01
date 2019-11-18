<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Register</title>
    <script type="text/javascript">
        function check(){
            var username = document.getElementById("username").value;
            var pwd = document.getElementById("password").value;
            var ckpwd = document.getElementById("check-password").value;
            alert(username+"\r\n"+pwd+"\r\n"+ckpwd);
            if(username==null){
                alert("用户名不能为空");
                return false;
            }
            if(pwd==null){
                alert("密码不能为空！");
                return false;
            }
            if(pwd!=ckpwd){
                alert("确认密码与原密码不相符，请重新填写！");
                return false;
            }
            return true;
        }
    </script>
</head>
<body>
    <form action="register" method="post" οnsubmit="return check()">
        <table>
            <tr>
                <td>账户：</td>
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
        </table>
        <input id="button" type="submit" value="注册">
    </form>
</body>
</html>
