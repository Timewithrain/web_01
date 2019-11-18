<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Register</title>
</head>
<body>
    <form action="register" method="post">
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
        <input type="submit" value="注册">
    </form>
</body>
</html>